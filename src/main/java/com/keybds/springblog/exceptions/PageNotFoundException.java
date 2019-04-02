package com.keybds.springblog.exceptions;

import com.keybds.springblog.enums.StatusMessageCode;

public class PageNotFoundException extends MvcException {

    public PageNotFoundException(StatusMessageCode statusMessageCode) {
        super(statusMessageCode);
    }

    public PageNotFoundException(StatusMessageCode statusMessageCode, Throwable t) {
        super(statusMessageCode, t);
    }

    public PageNotFoundException(Integer code, String message) {
        super(code, message);
    }

    public PageNotFoundException(Integer code, String message, Throwable t) {
        super(code, message, t);
    }
}
