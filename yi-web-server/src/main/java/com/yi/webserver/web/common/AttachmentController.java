package com.yi.webserver.web.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.yi.base.attachment.IAttachmentStorageExecutor;
import com.yi.base.attachment.IFileUrlGenerateService;
import com.yi.base.utils.Base64Utils;
import com.yi.core.attachment.domain.entity.Attachment;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yi.core.utils.ObjectPathUtils;
import com.yihz.common.annotation.session.CurrentUser;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import com.yihz.common.utils.web.WebUtils;

@Api(tags = "附件控制层")
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

	private final Logger LOG = LoggerFactory.getLogger(AttachmentController.class);

	@Resource
	private IAttachmentStorageExecutor attachmentStorageExecutor;

	@Resource
	private IFileUrlGenerateService fileUrlGenerateService;

	@Resource
	private IAttachmentService attachmentService;

	/**
	 * 分页查询附件列表, 所有查询条件都放在 data 这个MAP中, 当前支持objectPath,fileTypes,fileName三个参数
	 *
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "获取附件列表", notes = "根据分页参数查询附件列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AttachmentVo> query(@RequestBody Pagination<Attachment> query) {
		Page<AttachmentVo> page = attachmentService.queryVo(query);
		return page;
	}

	/**
	 * 按业务要素对象查询附件, 方法类似 findByPath
	 *
	 * @param objectType
	 * @param objectId
	 * @param includeSubPath
	 * @return
	 */
	@ApiOperation(value = "按业务要素对象查询附件", notes = "按业务要素对象查询附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "objectType", value = "对象类型", required = true, dataType = "ObjectType"),
			@ApiImplicitParam(name = "objectId", value = "对象Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "includeSubPath", value = "对象路径状态", required = false, dataType = "boolean") })
	@RequestMapping(value = "findByObjectId", method = { RequestMethod.GET, RequestMethod.POST })
	public RestResult findByObjectId(@RequestParam(name = "objectType") ObjectType objectType, @RequestParam(name = "objectId") int objectId,
			@RequestParam(name = "includeSubPath", required = false) boolean includeSubPath) {
		try {
			String path = ObjectPathUtils.concatObjectPath(objectType, objectId);
			List<AttachmentVo> list = attachmentService.findByPath(path, includeSubPath);
			return RestUtils.success(list);
		} catch (Exception ex) {
			LOG.error("附件获取失败", ex);
			return RestUtils.error("附件获取失败:" + ex.getMessage());
		}
	}

	/**
	 * 按业务要素对象路径查询附件
	 *
	 * @param objectPath
	 * @param includeSubPath
	 * @return
	 */
	@ApiOperation(value = "按业务要素对象路径查询附件", notes = "按业务要素对象路径查询附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "objectPath", value = "对象路径", required = true, dataType = "String"),
			@ApiImplicitParam(name = "includeSubPath", value = "对象路径状态", required = false, dataType = "boolean") })
	@RequestMapping(value = "findByPath", method = { RequestMethod.GET, RequestMethod.POST })
	public RestResult findByPath(@RequestParam(name = "objectPath") String objectPath, @RequestParam(name = "includeSubPath", required = false) boolean includeSubPath) {

		try {
			List<AttachmentVo> list = attachmentService.findByPath(objectPath, includeSubPath);
			return RestUtils.success(list);
		} catch (Exception ex) {
			LOG.error("附件获取失败", ex);
			return RestUtils.error("附件获取失败:" + ex.getMessage());
		}
	}

	@ApiOperation(value = "删除附件", notes = "删除附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "附件Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeById(@RequestParam(name = "id") int id) {
		try {
			attachmentService.remove(id);
			return RestUtils.success();

		} catch (Exception ex) {
			LOG.error("附件删除失败, id={}", id, ex);
			return RestUtils.error("附件删除失败:" + ex.getMessage());
		}
	}

	/**
	 * 将上传的文件内容保存到文件存储系统, 返回 AttachmentVo 列表<br>
	 * 具体的附件列表有前端提交到不同的业务数据保存接口, 由业务数据保存接口调用 AttachmentService.save 方法保存
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "上传附件", notes = "上传附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "currentUser", value = "用户", required = true, dataType = "User"), })
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public RestResult upload(@CurrentUser User currentUser, HttpServletRequest request) {
		try {
			List<MultipartFile> uploadFiles = WebUtils.findUploadFiles(request);
			if (uploadFiles == null || uploadFiles.isEmpty()) {
				return RestUtils.error("没有找到需要保存的附件");
			}

			List<AttachmentVo> attachments = Lists.newArrayList();
			for (MultipartFile uploadFile : uploadFiles) {
				String fileName = FilenameUtils.getName(uploadFile.getOriginalFilename());
				String fileExt = FilenameUtils.getExtension(fileName);
				FileType fileType = attachmentService.getFileType(fileExt);

				InputStream in = uploadFile.getInputStream();
				try {
					// 保存附件内容
					String fsGuid = attachmentStorageExecutor.save(in, fileExt);

					// 生成附件VO对象, 但不写入数据库
					Attachment attachment = new Attachment();
					attachment.setFsGuid(fsGuid);
					attachment.setFileName(fileName);
					attachment.setFileSize((int) uploadFile.getSize());
					attachment.setFileExt(fileExt);
					attachment.setFileType(fileType);
					attachment.setCreateTime(new Date());

					if (currentUser != null) {
						attachment.setUserId(currentUser.getId());
						attachment.setUserName(currentUser.getUsername());
					}

					AttachmentVo vo = attachmentService.toVo(attachment);
					// 由于此时没有保存到附件表, 所以只能通过 fsGuid 下载
					vo.setUrl(attachmentStorageExecutor.genFileUrl("/attachment/show/", attachment.getFsGuid(), null));

					attachments.add(vo);

				} finally {
					IOUtils.closeQuietly(in);
				}
			}

			if (attachments.isEmpty()) {
				return RestUtils.error("没有找到需要保存的附件");
			}

			return RestUtils.success(attachments);

		} catch (Exception ex) {
			LOG.error("附件上传失败", ex);
			return RestUtils.error("附件上传失败:" + ex.getMessage());
		}
	}

	/**
	 * 上传base64 图片
	 * 
	 * @param currentUser
	 * @param base64Str
	 * @return
	 */
	@ApiOperation(value = "上传附件", notes = "上传附件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "currentUser", value = "用户", required = true, dataType = "User"), })
	@RequestMapping(value = "uploadBase64", method = RequestMethod.POST)
	public RestResult uploadBase64(@CurrentUser User currentUser, HttpServletRequest request) {
		try {
			String base64Str = request.getParameter("base64Str");
			if (StringUtils.isBlank(base64Str)) {
				return RestUtils.error("没有找到需要保存的文件");
			}
			base64Str = base64Str.replaceAll(" ", "+");
			MultipartFile uploadFile = Base64Utils.base64ToMultipartFile(base64Str);
			if (uploadFile == null || uploadFile.isEmpty()) {
				return RestUtils.error("没有找到需要保存的附件");
			}
			List<AttachmentVo> attachments = Lists.newArrayList();
			String fileName = FilenameUtils.getName(uploadFile.getOriginalFilename());
			String fileExt = FilenameUtils.getExtension(fileName);
			FileType fileType = attachmentService.getFileType(fileExt);
			InputStream in = uploadFile.getInputStream();
			try {
				// 保存附件内容
				String fsGuid = attachmentStorageExecutor.save(in, fileExt);
				// 生成附件VO对象, 但不写入数据库
				Attachment attachment = new Attachment();
				attachment.setFsGuid(fsGuid);
				attachment.setFileName(fileName);
				attachment.setFileSize((int) uploadFile.getSize());
				attachment.setFileExt(fileExt);
				attachment.setFileType(fileType);
				attachment.setCreateTime(new Date());
				if (currentUser != null) {
					attachment.setUserId(currentUser.getId());
					attachment.setUserName(currentUser.getUsername());
				}
				AttachmentVo vo = attachmentService.toVo(attachment);
				// 由于此时没有保存到附件表, 所以只能通过 fsGuid 下载
				vo.setUrl(attachmentStorageExecutor.genFileUrl("/attachment/show/", attachment.getFsGuid(), null));
				attachments.add(vo);
			} finally {
				IOUtils.closeQuietly(in);
			}
			if (attachments.isEmpty()) {
				return RestUtils.error("没有找到需要保存的附件");
			}
			return RestUtils.success(attachments);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("附件上传失败", ex);
			return RestUtils.error("附件上传失败:" + ex.getMessage());
		}
	}

	/**
	 * 图片显示
	 * 
	 * @param uuid
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "图片显示", notes = "图片显示")
	@ApiImplicitParams({ @ApiImplicitParam(name = "filename", value = "文件名", required = true, dataType = "String"), })
	@RequestMapping(value = "/show/{filename:.+}", method = RequestMethod.GET)
	public RestResult show(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) {
		String uuid = FilenameUtils.getBaseName(filename);
		try (InputStream inputStream = attachmentStorageExecutor.open(uuid); OutputStream outputStream = response.getOutputStream()) {
			byte[] context = IOUtils.toByteArray(inputStream);
			response.reset();
			response.setContentType(URLConnection.getFileNameMap().getContentTypeFor(filename));
			response.addHeader("Content-Length", String.valueOf(context.length));
			if (context != null) {
				outputStream.write(context, 0, context.length);
			}
			return RestUtils.success("图片下载成功");
		} catch (Exception ex) {
			LOG.error("图片下载失败", ex);
			return RestUtils.error("图片下载失败:" + ex.getMessage());
		}

	}
}
