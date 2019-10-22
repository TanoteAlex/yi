package com.yi.searchServer.web.search.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CombinationVO implements Serializable {
    private static final long serialVersionUID = -4056309732076882904L;
    private Map<String, List<Object>> condition;

    private List<PriceSection> priceSection;

    private Set<Category> categories = new HashSet<>();

    public Map<String, List<Object>> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, List<Object>> condition) {
        this.condition = condition;
    }

    public List<PriceSection> getPriceSection() {
        return priceSection;
    }

    public void setPriceSection(List<PriceSection> priceSection) {
        this.priceSection = priceSection;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
