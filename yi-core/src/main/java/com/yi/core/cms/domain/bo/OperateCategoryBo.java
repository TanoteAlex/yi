/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.bo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 运营分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class OperateCategoryBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 运营分类ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;

	/**
	 * 上级运营分类ID
	 */
	private OperateCategoryBo parent;

	/**
	 * 下级运营分类
	 */
	private Set<OperateCategoryBo> children;
	/**
	 * 运营分类编码
	 */
	@Length(max = 16)
	private String categoryNo;
	/**
	 * 运营分类名称
	 */
	@NotBlank
	@Length(max = 32)
	private String categoryName;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 图片路径
	 */
	@Length(max = 255)
	private String imgPath;
	/**
	 * 排序
	 */
	@Max(127)
	private Integer sort;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 链接类型（1商品2文章3活动4专区）
	 */
	private Integer linkType;
	/**
	 * 链接ID
	 */
	private Integer linkId;

	/**
	 * 是否显示分类名称
	 */
	private Integer showName;

	private List<CommodityBo> commodities;


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public OperateCategoryBo getParent() {
		return parent;
	}

	public void setParent(OperateCategoryBo parent) {
		this.parent = parent;
	}

	public Set<OperateCategoryBo> getChildren() {
		return children;
	}

	public void setChildren(Set<OperateCategoryBo> children) {
		this.children = children;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public Integer getShowName() {
		return showName;
	}

	public void setShowName(Integer showName) {
		this.showName = showName;
	}

	public List<CommodityBo> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<CommodityBo> commodities) {
		this.commodities = commodities;
	}
}