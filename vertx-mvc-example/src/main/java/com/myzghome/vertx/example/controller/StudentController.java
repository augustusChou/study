package com.myzghome.vertx.example.controller;

import com.alibaba.fastjson.JSON;
import com.myzghome.core.annotation.Loading;
import com.myzghome.core.annotation.Register;
import com.myzghome.vertx.example.module.Student;
import com.myzghome.vertx.example.service.StudentService;
import com.myzghome.vertx.example.tool.JsonResult;
import com.myzghome.vertx.mvc.annotation.controller.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：周广
 * 创建时间：2017/1/19 0019
 * 必要描述:
 */
@Register
@Api(path = "student")
public class StudentController {

    private static final String successResult = JSON.toJSONString(JsonResult.SUCESS);
    private static final String failResult = JSON.toJSONString(JsonResult.FAIL());
    @Loading
    private StudentService studentService;
    private Map<Integer, Student> studentMap = new HashMap<>();

    @Get
    public JsonResult getAll() {
        return new JsonResult(studentService.getAll());
    }

    @Get(path = ":id")
    public JsonResult getById(String a) {
        if (a != null) {
            return new JsonResult(studentService.getById(a));
        }
        return JsonResult.FAIL;
    }

    @Post
    public JsonResult addStudent(String id, Student student) {
        if (student != null) {
            studentService.add(student);
            return JsonResult.SUCESS;
        }
        return JsonResult.FAIL;
    }

    @Put(path = ":id")
    public JsonResult put(String param, Student student) {
        if (student != null) {
            studentService.update(param, student);
            return JsonResult.SUCESS;
        }
        return JsonResult.FAIL;
    }

    @Delete(path = ":id")
    public JsonResult delete(String id) {
        if (id != null) {
            studentService.delete(id);
            return JsonResult.SUCESS;
        }
        return JsonResult.FAIL;
    }
}
