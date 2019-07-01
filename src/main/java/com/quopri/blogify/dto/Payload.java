package com.quopri.blogify.dto;

public class Payload {

    private String data;

    private String code;

    public Payload() {

    }

    public Payload(String data, String code) {
        this.data = data;
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
