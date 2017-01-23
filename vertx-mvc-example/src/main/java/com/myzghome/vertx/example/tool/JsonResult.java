package com.myzghome.vertx.example.tool;

public class JsonResult<T> {

    //处理成功
    public static final String OK = "000";
    //未知错误
    public static final String ERR = "-1";
    //超时或未登录

    public static final JsonResult SUCESS = new JsonResult(OK, "操作成功！");
    public static final JsonResult FAIL = new JsonResult(ERR, "系统繁忙");

    //返回结果定义,默认返回"-1",请求失败
    private String result = "-1";

    private String desc = "";

    private T data;


    public JsonResult() {
    }

    public JsonResult(String code, String message) {
        this.result = code;
        this.desc = message;
    }

    public JsonResult(String code, String message, T result) {
        this.result = code;
        this.desc = message;
        this.data = result;
    }

    public JsonResult(T result) {
        this(OK, "操作成功！", result);
    }

    /**
     * 失败
     */
    public static JsonResult getFailResult(String message) {
        return new JsonResult(ERR, message);
    }

    /**
     * 成功
     */
    public static JsonResult getSuccessResult(String message) {
        return new JsonResult(OK, message);
    }

    /**
     * 成功
     */
    public static JsonResult getSuccessResult(Object data, String message) {
        return new JsonResult(OK, message, data);
    }

    public static JsonResult FAIL() {
        return FAIL;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;

        final JsonResult ret = (JsonResult) obj;
        return result == ret.getResult();
    }

    public String getDesc() {
        return desc;
    }

    public JsonResult setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getResult() {
        return result;
    }

    public JsonResult setResult(String result) {
        this.result = result;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(T data) {
        this.data = data;
        return this;
    }
}
