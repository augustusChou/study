package com.myzghome.vertx.mvc.exception;

import lombok.Data;

/**
 * 作者：周广
 * 创建时间：2017/1/24 0024
 * 必要描述:
 */
@Data
public class Result {

    public static final String FAIL = "-1";
    private String result;

    private String desc;

    public Result() {
    }

    public Result(String result, String desc) {
        this.result = result;
        this.desc = desc;
    }

}
