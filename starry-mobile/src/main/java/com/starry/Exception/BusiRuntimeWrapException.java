package com.starry.Exception;


/**
 *  RuntimeException包装类
 */
public class BusiRuntimeWrapException extends RuntimeException {

    private Integer errorCode;

    public BusiRuntimeWrapException(Exception t) {
        super(t);
    }

    public BusiRuntimeWrapException(Integer code) {
        this.errorCode = code;
    }

    public Exception getException() {
        return (Exception)this.getCause();
    }

    public Integer getErrorCode(){
        return errorCode;
    }
}
