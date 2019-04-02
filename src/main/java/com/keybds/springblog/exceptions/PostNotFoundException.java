package com.keybds.springblog.exceptions;

import com.keybds.springblog.enums.StatusMessageCode;

public class PostNotFoundException extends MvcException {

    public PostNotFoundException(StatusMessageCode statusMessageCode) {
        super(statusMessageCode);
    }
}
