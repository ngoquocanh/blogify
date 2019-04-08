package com.keybds.springblog.enums;

public enum StatusMessageCode {
    SUCCESS(2000, "Success"),
    FAIL(4000, "Unknown error"),
    ERROR(5000, "System error"),
    POST_NOT_FOUND(1001, "Post not found"),
    POST_EXISTED(1003, "Post Existed"),
    DATABASE_ERROR(1002, "Database Error"),
    ACCOUNT_NOT_FOUND(1003, "Account not found"),
    PAGE_NOT_FOUND(404, "Page not found");

    private Integer code;
    private String message;

    private StatusMessageCode(Integer code, String message) {
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
