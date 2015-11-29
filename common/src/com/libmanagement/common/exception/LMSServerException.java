package com.libmanagement.common.exception;


public class LMSServerException extends RuntimeException {
    private int code = -1;

    public LMSServerException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public LMSServerException(String msg) {
        super(msg);
    }

    public LMSServerException() {
        super();
    }

    public int getCode() {
        return code;
    }
}
