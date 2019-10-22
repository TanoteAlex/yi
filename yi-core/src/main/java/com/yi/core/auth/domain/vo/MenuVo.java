package com.yi.core.auth.domain.vo;

import java.util.List;

/**
 * @ClassName MenuVo
 * @Author jstin
 * @Date 2019/1/16 10:17
 * @Version 1.0
 **/
public class MenuVo {
    private String text;

    private boolean group;

    private boolean hideInBreadcrumb;

    private List<ChildrensVo> children;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public void setHideInBreadcrumb(boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public List<ChildrensVo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrensVo> children) {
        this.children = children;
    }
}
