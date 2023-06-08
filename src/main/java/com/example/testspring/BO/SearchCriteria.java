package com.example.testspring.BO;

public class SearchCriteria {

    private String key;
    private String condition;
    private Object value;

    public SearchCriteria(String key, String condition, Object value) {
        this.key = key;
        this.condition = condition;
        this.value = value;
    }


    public SearchCriteria() {

    }


    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
