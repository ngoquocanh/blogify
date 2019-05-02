package com.keybds.blogify.exceptions;

import com.keybds.blogify.enums.StatusMessageCode;

public class MvcException extends Exception {

    public MvcException(StatusMessageCode statusMessageCode) {
        super(statusMessageCode.getMessage());
        this.setCode(statusMessageCode.getCode());
    }

    public MvcException(StatusMessageCode statusMessageCode, Throwable t) {
        super(statusMessageCode.getMessage(), t);
        this.setCode(statusMessageCode.getCode());
    }

    public MvcException(Integer code, String message) {
        super(message);
        this.setCode(code);
    }

    public MvcException(Integer code, String message, Throwable t) {
        super(message, t);
        this.setCode(code);
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
