package com.keybds.springblog.enums;

public enum ArticleEnum {
    STATUS_PUBLIC(1, "Public"),
    STATUS_DRAFT(2, "Draft"),
    TYPE_POST(1, "Post"),
    TYPE_PAGE(2, "Page");

    private Integer key;
    private String value;

    private ArticleEnum(Integer key, String value) {
        this.setKey(key);
        this.setValue(value);
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getKey() + " " + this.getValue();
    }
}
