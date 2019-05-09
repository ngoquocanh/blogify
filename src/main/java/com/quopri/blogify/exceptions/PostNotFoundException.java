package com.quopri.blogify.exceptions;

import com.quopri.blogify.enums.StatusMessageCode;

public class PostNotFoundException extends MvcException {

    public PostNotFoundException(StatusMessageCode statusMessageCode) {
        super(statusMessageCode);
    }
}
