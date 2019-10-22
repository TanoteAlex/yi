/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.member.controller;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("会员控制层")
@RestController
@RequestMapping(value = "/member")
public class MemberController {

	private final Logger LOG = LoggerFactory.getLogger(MemberController.class);

	@Resource
	private IMemberService memberService;

	@Resource
	private IMemberLevelService memberLevelService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取会员列表", notes = "根据分页参数查询会员列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MemberListVo> queryMember(@RequestBody Pagination<Member> query) {
		Page<MemberListVo> page = memberService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看会员信息", notes = "根据会员Id获取会员信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewMember(@RequestParam("id") int memberId) {
		try {
			MemberVo member = memberService.getMemberVoById(memberId);
			return RestUtils.successWhenNotNull(member);
		} catch (BusinessException ex) {
			LOG.error("get Member failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 会员等级
	 **/
	@ApiOperation(value = "获取所有会员等级信息", notes = "获取所有会员等级信息")
	@RequestMapping(value = "memberLevel", method = RequestMethod.GET)
	public List<MemberLevelListVo> memberLevel() {
		return memberLevelService.queryAll();
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增会员", notes = "添加会员")
	@ApiImplicitParams({ @ApiImplicitParam(name = "member", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addMember(@RequestBody MemberBo member) {
		try {
			MemberListVo dbMember = memberService.addMember(member);
			return RestUtils.successWhenNotNull(dbMember);
		} catch (BusinessException ex) {
			LOG.error("add Member failure : {}", member, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新会员", notes = "修改会员")
	@ApiImplicitParams({ @ApiImplicitParam(name = "member", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateMember(@RequestBody MemberBo member) {
		try {
			MemberListVo dbMember = memberService.updateMember(member);
			return RestUtils.successWhenNotNull(dbMember);
		} catch (BusinessException ex) {
			LOG.error("update Member failure : {}", member, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 重置会员密码
	 **/
	@ApiOperation(value = "重置会员密码", notes = "重置会员密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "member", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	public RestResult resetPassword(@RequestParam("id") int memberId) {
		try {
			MemberBo member=new MemberBo();
			member.setId(memberId);
			MemberListVo dbMember = memberService.resetPassword(member);
			return RestUtils.successWhenNotNull(dbMember);
		} catch (BusinessException ex) {
			LOG.error("reset Password failure : {}", memberId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除会员", notes = "删除会员")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeMemberById(@RequestParam("id") int memberId) {
		try {
			memberService.removeMemberById(memberId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Member failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 禁用启用会员
	 **/
	@ApiOperation(value = "禁用启用会员", notes = "禁用启用会员")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "prohibition", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("memberId") int memberId) {
		try {
			MemberVo member = memberService.updateState(memberId);
			return RestUtils.success(member);
		} catch (Exception ex) {
			LOG.error("remove Member failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 取消vip
	 **/
	@ApiOperation(value = "取消vip", notes = "取消vip")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updataVipNo", method = RequestMethod.GET)
	public RestResult updataVipNo(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(memberService.updataVipNo(memberId));
		} catch (Exception ex) {
			LOG.error("updata Member vip failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 成为vip
	 **/
	@ApiOperation(value = "成为vip", notes = "成为vip")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updataVipYes", method = RequestMethod.GET)
	public RestResult updataVipYes(@RequestParam("memberId") int memberId) {
		try {

			return RestUtils.success(memberService.updataVipYes(memberId));
		} catch (Exception ex) {
			LOG.error("updata Member vip failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}