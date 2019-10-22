package com.yi.core.auth.domain.vo;

import java.util.List;

/**
 * @ClassName ChildrensVo
 * @Author jstin
 * @Date 2019/1/16 10:07
 * @Version 1.0
 **/
public class ChildrensVo {
    private String text;

    private String icon;

    private List<ChildrenVo> children;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ChildrenVo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenVo> children) {
        this.children = children;
    }
}
