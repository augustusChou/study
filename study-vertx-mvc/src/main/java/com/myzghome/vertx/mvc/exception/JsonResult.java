package com.myzghome.vertx.mvc.exception;

import lombok.Data;

/**
 * 作者：周广
 * 创建时间：2017/1/24 0024
 * 必要描述:
 */
@Data
public class JsonResult {

    public static final String FAIL = "FAIL";
    private String status;
    private String err_msg;

    public JsonResult() {
    }

    public JsonResult(String status, String err_msg) {
        this.status = status;
        this.err_msg = err_msg;
    }

}
