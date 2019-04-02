package com.keybds.springblog.enums;

public enum RoleEnum {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer key;
    private String value;

    private RoleEnum(Integer key, String value) {
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
