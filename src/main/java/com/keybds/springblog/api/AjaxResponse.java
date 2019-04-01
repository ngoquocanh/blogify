package com.keybds.springblog.api;

import com.keybds.springblog.enums.StatusMessageCode;
import lombok.Data;

@Data
public class AjaxResponse {
    private Integer code;
    private String message;
    private Object data;

    public AjaxResponse(StatusMessageCode messageCode) {
        this.setCode(messageCode.getCode());
        this.setMessage(messageCode.getMessage());
        this.data = null;
    }
}
