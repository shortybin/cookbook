package com.wuhuabin.cookbook.exception;

public class CookBookException extends RuntimeException {
    private final Integer code;
    private final String message;


    public CookBookException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CookBookException(CookBookExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
