package com.wuhuabin.cookbook.exception;

/**
 * 描述： 异常枚举
 */
public enum CookBookExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能小于6位"),
    NAME_EXISTED(10004, "用户已存在"),
    INSERT_FAILED(10005, "插入失败，请重试"),
    WRONG_PASSWORD(10006, "密码错误"),
    NEED_LOGIN(10007, "用户未登录"),
    NAME_NOT_NULL(10008, "参数不能为空"),
    CREATE_FAILED(10009, "新增失败"),
    REQUEST_PARAM_ERROR(10010, "参数错误"),
    MKDIR_FAILED(10011, "文件创建失败"),
    UPLOAD_FAILED(10012, "文件上传失败"),
    USER_NOT_EXIST(10013, "用户不存在"),
    SYSTEM_ERROR(20001, "系统异常");

    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    CookBookExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}