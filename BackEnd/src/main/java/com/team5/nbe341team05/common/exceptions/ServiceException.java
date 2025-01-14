package com.team5.nbe341team05.common.exceptions;

import com.team5.nbe341team05.common.response.ResponseMessage;

public class ServiceException extends RuntimeException{
    private final String resultCode;
    private final String msg;

    public ServiceException(String resultCode, String msg) {
        super(resultCode + " : " + msg);
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public ResponseMessage<Void> getRsData() {
        return new ResponseMessage<>(msg, resultCode,null);
    }
}
