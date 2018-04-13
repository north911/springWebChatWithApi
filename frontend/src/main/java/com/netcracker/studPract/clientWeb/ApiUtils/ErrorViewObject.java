package com.netcracker.studPract.clientWeb.ApiUtils;

import org.springframework.stereotype.Component;

@Component
public class ErrorViewObject {

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
