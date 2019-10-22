package com.yi.core.attachment.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yihz.common.convert.EntityListVoConvert;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yi.base.attachment.IAttachmentStorageExecutor;
import com.yi.base.attachment.IFileUrlGenerateService;
import com.yi.core.attachment.dao.AttachmentDao;
import com.yi.core.attachment.domain.entity.Attachment;
import com.yi.core.attachment.domain.entity.Attachment_;
import com.yi.core.attachment.domain.vo.AttachmentListVo;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectInfo;
import com.yi.core.common.ObjectType;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.persistence.Pagination;

/**
 * 附件服务
 *
 * @author Administrator
 */
@Service
@Transactional
public class AttachmentServiceImpl implements IAttachmentService, InitializingBean {

    @Resource
    private BeanConvertManager beanConvertManager;

    @Autowired
    private IAttachmentStorageExecutor attachmentStorageExecutor;

    // @Autowired
    // private IObjectInfoService objectInfoService;

    @Autowired
    private IFileUrlGenerateService fileUrlGenerateService;

    @Autowired
    private AttachmentDao attachmentDao;

    private EntityListVoConvert<Attachment, AttachmentVo, AttachmentListVo> attachmentConvert;

    // /**
    // * 缩略图宽
    // */
    // @Value("${yi.attachment.thumbnail.width:200}")
    // private int thumbnailWidth;
    //
    // /**
    // * 缩略图高
    // */
    // @Value("${yi.attachment.thumbnail.height:200}")
    // private int thumbnailHeight;

    final private Map<String, FileType> fileExtTypeMap = Maps.newHashMap();

    /**
     * 分页查询
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Attachment> query(Pagination<Attachment> query) {
        query.setEntityClazz(Attachment.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {

        }));
        Page<Attachment> page = attachmentDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<AttachmentVo> queryVo(Pagination<Attachment> query) {
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            // 供应商
            Object supplierId = query.getParams().get("supplier.id");
            if (supplierId != null) {
                list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Attachment_.supplierId), supplierId)));
            }
        }));
        Page<Attachment> pages = this.query(query);
        List<AttachmentVo> vos = attachmentConvert.toVos(pages.getContent());
        return new PageImpl<AttachmentVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    // /**
    // * 分页查询附件列表, 所有查询条件都放在 data 这个MAP中, 当前支持objectPath,fileTypes,fileName三个参数
    // *
    // * @param query
    // * @return
    // */
    // @Override
    // public Page<AttachmentVo> query(Pagination query) {
    // query.setEntityClazz(Attachment.class);
    //
    // final Map params = query.getParams();
    // // if (MapUtils.isEmpty(params)) {
    // // throw new BusinessException("invalidArguments", "附件查询参数没有设置");
    // // }
    //
    // Page<Attachment> page = attachmentDao.findAll(new Specification<Attachment>()
    // {
    // @Override
    // public Predicate toPredicate(Root<Attachment> root, CriteriaQuery<?> query,
    // CriteriaBuilder cb) {
    // List<Predicate> predicates = Lists.newArrayList();
    //
    // // 对象路径查找
    // String objectPath = StringUtils.trimToNull(ValueUtils.getString(params,
    // "objectPath"));
    // if (objectPath != null) {
    // if (StringUtils.endsWith(objectPath, "%")) {
    // // 模糊查找, 字符串以百分号结束时
    // // predicates.add(cb.like(root.get(Attachment_.objectPath), objectPath));
    //
    // } else {
    // // predicates.add(cb.equal(root.get(Attachment_.objectPath), objectPath));
    // }
    // }
    //
    // // 文件类型
    // List<String> fileTypes = ValueUtils.toStringList(params.get("fileTypes"));
    // if (CollectionUtils.isNotEmpty(fileTypes)) {
    // // predicates.add(JpaUtils.equalOrIn(cb, root.get(Attachment_.fileType),
    // // fileTypes));
    // }
    //
    // // 文件名称模糊查找
    // String fileName = StringUtils.trimToNull(ValueUtils.getString(params,
    // "fileName"));
    // if (fileName != null) {
    // // predicates.add(cb.like(root.get(Attachment_.fileName), "%" + fileName +
    // // "%"));
    // }
    //
    // // TODO: 附件上传业务场景查询, 如沟通附件, 回顾分析附件, 定义附件等
    //
    // // if (predicates.size() == 0) {
    // // throw new BusinessException("noAttachmentQueryParams", "没有找到有效的附件查询参数");
    // // }
    // // if (predicates.size() == 0) {
    // // return predicates.get(0);
    // // }
    // return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    // }
    // }, query.getPageRequest());
    //
    // List<AttachmentVo> vos = page.getContent().stream().map(attachment -> {
    // AttachmentVo vo = attachmentVoBeanConvert.convert(attachment);
    // vo.setObjectInfos(parseObjectInfos(attachment));
    // return vo;
    // }).collect(Collectors.toList());
    //
    // Page<AttachmentVo> voPage = new PageImpl<AttachmentVo>(vos,
    // query.getPageRequest(), page.getTotalElements());
    // return voPage;
    // }

    /**
     * 根据附加的 objectPath 解析所属战略要素信息
     *
     * @param attachment
     * @return
     */
    private Collection<ObjectInfo> parseObjectInfos(Attachment attachment) {
        List<ObjectInfo> objectInfos = Lists.newArrayList();

        // String[] strArray = StringUtils.split(attachment.getObjectPath(), "/");
        // for (int index = 0; index + 1 < strArray.length; index += 2) {
        // int objectId = ValueUtils.toInteger(strArray[index + 1], 0);
        // ObjectType objectType = ObjectPathUtils.toObjectType(strArray[index]);
        // if (objectType == null || objectId <= 0) {
        // continue;
        // }
        //
        // ObjectInfo objectInfo = objectInfoService.getObjectInfo(objectType,
        // objectId);
        // if (objectInfo != null) {
        // objectInfos.add(objectInfo);
        // }
        // }

        return objectInfos;
    }

    @Override
    public AttachmentVo getVoById(int attachId, boolean includeObjectInfos) {
        Attachment record = attachmentDao.getOne(attachId);
        if (record == null) {
            return null;
        }

        AttachmentVo vo = attachmentConvert.toVo(record);
        if (includeObjectInfos) {
            vo.setObjectInfos(parseObjectInfos(record));
        }

        return vo;
    }

    @Override
    public AttachmentVo toVo(Attachment attachment) {
        AttachmentVo vo = attachmentConvert.toVo(attachment);
        return vo;
    }

    @Override
    public int getCount(String objectPath, boolean includeSubPath) {
        String path = StringUtils.upperCase(objectPath);

        long count;
        if (includeSubPath) {
            count = attachmentDao.count((root, query, cb) -> {
                return cb.like(root.get(Attachment_.objectPath), path + "%");
                // return null;
            });

        } else {
            count = attachmentDao.count((root, query, cb) -> {
                return cb.equal(root.get(Attachment_.objectPath), path);
                // return null;
            });
        }

        return (int) count;
    }

    @Override
    public List<AttachmentVo> findByPath(String objectPath, boolean includeSubPath) {
        String path = StringUtils.upperCase(objectPath);

        List<Attachment> list;
        if (includeSubPath) {
            list = attachmentDao.findAll((root, query, cb) -> {
                return cb.like(root.get(Attachment_.objectPath), path + "%");
                // return null;
            });

        } else {
            list = attachmentDao.findAll((root, query, cb) -> {
                return cb.equal(root.get(Attachment_.objectPath), path);
                // return null;
            });
        }

        return attachmentConvert.toVos(list);
    }

    @Override
    public void save(String objectPath, Collection<AttachmentVo> attachments, boolean deleteByIdOldFiles) {
        objectPath = StringUtils.upperCase(objectPath);

        if (deleteByIdOldFiles) {
            attachmentDao.deleteByPath(objectPath);
        }

        if (CollectionUtils.isEmpty(attachments)) {
            return;
        }

        // final User user = getCurrentUser();
        //
        // for (AttachmentVo attachment : attachments) {
        // Attachment record = new Attachment();
        // copyProperties(attachment, record);
        //
        // record.setObjectPath(objectPath);
        //
        // if (record.getUserId() <= 0 || record.getUserName() == null) {
        // record.setUserId(user.getId());
        // record.setUserName(user.getUsername());
        // }
        // attachmentDao.save(record);
        // }
    }

    @Override
    public AttachmentVo add(AttachmentVo attachment) {
        Attachment record = new Attachment();
        copyProperties(attachment, record);
        if (record.getUserId() <= 0 || record.getUserName() == null) {
            // final User user = getCurrentUser();
            // k,
            // record.setUserId(user.getId());
            // record.setUserName(user.getUsername());
        }

        Attachment dbRecord = attachmentDao.save(record);
        return attachmentConvert.toVo(dbRecord);
    }

    /**
     * 拷贝附件VO属性到附件实体对象
     *
     * @param vo
     * @param record
     */
    private void copyProperties(AttachmentVo vo, Attachment record) {
        beanConvertManager.copying(vo, record, Attachment_.objectType, Attachment_.filePath, Attachment_.objectId,
                Attachment_.fileName, Attachment_.fileSize,
                Attachment_.createTime, Attachment_.userName, Attachment_.userId, Attachment_.description);
        record.setObjectPath(StringUtils.upperCase(vo.getObjectPath()));

        String fileExt = StringUtils.lowerCase(FilenameUtils.getExtension(record.getFileName()));
        record.setFileExt(fileExt);
        record.setFileType(getFileType(fileExt));

        // 生成附件下载链接
        record.setFilePath(vo.getUrl());
    }

    @Override
    public void remove(int attachmentId) {
        attachmentDao.deleteById(attachmentId);
    }

    @Override
    public void removeByPath(String objectPath, boolean includeSubPath) {
        if (includeSubPath) {
            attachmentDao.deleteByPathLike(objectPath + "%");
        } else {
            attachmentDao.deleteByPath(objectPath);
        }
    }

    @Override
    public InputStream getInputStream(int attachmentId) throws IOException {
        Attachment record = attachmentDao.getOne(attachmentId);
        if (record == null) {
            throw new RuntimeException("找不到附件资料");
        }

        return attachmentStorageExecutor.open(record.getFsGuid());
    }

    @Override
    public Attachment getById(int attachmentId) {
        Attachment record = attachmentDao.getOne(attachmentId);
        return record;
    }

    /**
     * 根据文件扩展名获取对应的文件类型
     *
     * @param fileExt
     * @return
     */
    @Override
    public FileType getFileType(String fileExt) {
        FileType type = fileExtTypeMap.getOrDefault(fileExt, FileType.OTHERS);
        return type;
    }

    // @Override
    // protected IDeptService getDeptService() {
    // return deptService;
    // }

    // @Override
    // protected IUserService getUserService() {
    // return userService;
    // }

    private void configFileExtTypes(FileType fileType, String... fileExts) {
        for (int index = 0; index < fileExts.length; ++index) {
            fileExtTypeMap.put(fileExts[index], fileType);
        }
    }

    // @Override
    // public void afterPropertiesSet() throws Exception {
    // configFileExtTypes(FileType.PICTURES, "bmp", "jpg", "jpeg", "png", "gif",
    // "svg");
    // configFileExtTypes(FileType.DOCUMENT, "doc", "docx", "ppt", "pptx", "xls",
    // "xlsx", "wps", "rtf", "txt", "pdf");
    // configFileExtTypes(FileType.COMPRESSION, "zip", "rar", "tar", "gz", "7z",
    // "cab", "jar", "arj", "lzh", "ace", "bz2");
    // configFileExtTypes(FileType.VIDEOS, "avi", "ra", "rmv", "rmvb", "asf",
    // "divx", "mkv", "mpe", "mpg", "mpeg", "mp4", "vob", "mov", "qt");
    //
    // this.attachmentBeanConvert = new AbstractBeanConvert<Attachment,
    // AttachmentVo>(beanConvertManager) {
    // @Override
    // protected void postConvert(AttachmentVo vo, Attachment attachment) {
    // // if (attachment.getAttachId() > 0) {
    // // // 使用附件链接下载, 支持显示用户上传的原始文件名
    // //
    // vo.setUrl(fileUrlGenerateService.genAttachmentUrl(attachment.getAttachId(),
    // // null, null));
    // //
    // // } else {
    // // vo.setUrl(fileUrlGenerateService.genFileUrl(attachment.getFsGuid(),
    // // attachment.getFileExt()));
    // // }
    //
    // // TODO: 视频,压缩,文档等的缩略图待实现
    // // if (FileType.PICTURES == attachment.getFileType()) {
    // //
    // vo.setThumbnailUrl(fileUrlGenerateService.genImageFileUrl(attachment.getFsGuid(),
    // // attachment.getFileExt(), thumbnailWidth, thumbnailHeight));
    // // }
    // }
    // };
    // }

    @Override
    public void deleteByObjectId(Integer objectId) {
        if (objectId != null) {
            attachmentDao.deleteByObjectId(objectId);
        }
    }

    @Override
    public void deleteByObjectIdAndObjectType(Integer objectId, ObjectType objectType) {
        if (objectId != null && objectType != null) {
            attachmentDao.deleteByObjectIdAndObjectType(objectId, objectType);
        }
    }

    @Override
    public void saveAll(List<AttachmentVo> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<Attachment> saveList = new ArrayList<>();
            for (AttachmentVo tmp : list) {
                if (tmp != null) {
                    Attachment attachment = new Attachment();
                    attachment.setFsGuid(tmp.getFsGuid());
                    attachment.setObjectType(tmp.getObjectType());
                    attachment.setFilePath(tmp.getUrl());
                    attachment.setObjectId(tmp.getObjectId());
                    attachment.setObjectPath(tmp.getObjectPath());
                    attachment.setFileName(tmp.getFileName());
                    attachment.setFileExt(tmp.getFileExt());
                    attachment.setFileType(tmp.getFileType());
                    attachment.setFileSize(tmp.getFileSize());
                    attachment.setCreateTime(tmp.getCreateTime());
                    attachment.setUserId(tmp.getUserId());
                    attachment.setUserName(tmp.getUserName());
                    attachment.setSupplierId(tmp.getSupplierId());
                    attachment.setSupplierName(tmp.getSupplierName());
                    attachment.setDescription(tmp.getDescription());
                    saveList.add(attachment);
                }
            }
            if (CollectionUtils.isNotEmpty(saveList)) {
                attachmentDao.saveAll(saveList);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<AttachmentVo> findByObjectIdAndObjectType(Integer objectId, ObjectType objectType) {
        List<Attachment> attachments = attachmentDao.findByObjectIdAndObjectType(objectId, objectType);
        if (CollectionUtils.isNotEmpty(attachments)) {
            return attachmentConvert.toVos(attachments);
        }
        return null;
    }

    @Override
    public void deleteList(List<AttachmentVo> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (AttachmentVo tmp : list) {
                if (tmp != null && tmp.getAttachId() > 0) {
                    attachmentDao.deleteById(tmp.getAttachId());
                }
            }
        }
    }

    protected void initConvert() {
        configFileExtTypes(FileType.PICTURES, "bmp", "jpg", "jpeg", "png", "gif", "svg");
        configFileExtTypes(FileType.DOCUMENT, "doc", "docx", "ppt", "pptx", "xls", "xlsx", "wps", "rtf", "txt", "pdf");
        configFileExtTypes(FileType.COMPRESSION, "zip", "rar", "tar", "gz", "7z", "cab", "jar", "arj", "lzh", "ace",
                "bz2");
        configFileExtTypes(FileType.VIDEOS, "avi", "ra", "rmv", "rmvb", "asf", "divx", "mkv", "mpe", "mpg", "mpeg",
                "mp4", "vob", "mov", "qt");

        this.attachmentConvert = new EntityListVoConvert<Attachment, AttachmentVo, AttachmentListVo>
                (beanConvertManager) {
            @Override
            protected BeanConvert<Attachment, AttachmentVo> createEntityToVoConvert(BeanConvertManager
                                                                                            beanConvertManager) {
                return new AbstractBeanConvert<Attachment, AttachmentVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(AttachmentVo attachmentVo, Attachment attachment) {
                    }
                };
            }

            @Override
            protected BeanConvert<Attachment, AttachmentListVo> createEntityToListVoConvert(BeanConvertManager
                                                                                                    beanConvertManager) {
                return new AbstractBeanConvert<Attachment, AttachmentListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(AttachmentListVo attachmentListVo, Attachment attachment) {

                    }
                };
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConvert();
    }

}
