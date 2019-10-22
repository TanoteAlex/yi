/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.CategorySimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import java.util.Date;
import java.util.Set;

/**
 * 分类
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CategoryVo extends VoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // columns START
    /**
     * 分类ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 上级分类
     */
    private CategorySimple parent;
    private int parentId;
    private String parentName;
    /**
     * 下级分类
     */
    private Set<CategorySimple> children;
    /**
     * 分类编码
     */
    @Length(max = 16)
    private String categoryNo;
    /**
     * 分类名称
     */
    @Length(max = 32)
    private String categoryName;
    /**
     * 图片路径
     */
    @Length(max = 127)
    private String imgPath;
    /**
     * 排序
     */
    @Max(127)
    private int sort;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date createTime;
    /**
     * 备注
     */
    @Length(max = 127)
    private String remark;
    /**
     * 删除（0否1是）
     */
    private Integer deleted;
    /**
     * 删除时间
     */
    private Date delTime;
    // columns END


    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

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

    public CategorySimple getParent() {
        return parent;
    }

    public void setParent(CategorySimple parent) {
        this.parent = parent;
    }

    public Set<CategorySimple> getChildren() {
        return children;
    }

    public void setChildren(Set<CategorySimple> children) {
        this.children = children;
    }

    public String getCategoryNo() {
        return this.categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

}