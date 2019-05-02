package com.keybds.blogify.exceptions;

import com.keybds.blogify.enums.StatusMessageCode;

public class PostNotFoundException extends MvcException {

    public PostNotFoundException(StatusMessageCode statusMessageCode) {
        super(statusMessageCode);
    }
}
