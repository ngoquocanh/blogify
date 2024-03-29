package com.quopri.blogify.enums;

public enum StatusMessageCode {
    SUCCESS(2000, "Success"),
    FAIL(4000, "Unknown error"),
    ERROR(5000, "System error"),
    POST_NOT_FOUND(1001, "Post not found"),
    CATEGORY_NOT_FOUND(1004, "Category not found"),
    TAG_NOT_FOUND(1005, "Tag not found"),
    POST_EXISTED(1003, "Post Existed"),
    DATABASE_ERROR(1002, "Database Error"),
    ACCOUNT_NOT_FOUND(1003, "Account not found"),
    PAGE_NOT_FOUND(404, "Page not found");

    private Integer code;
    private String message;

    StatusMessageCode(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getCode() + " " + this.getMessage();
    }
}
