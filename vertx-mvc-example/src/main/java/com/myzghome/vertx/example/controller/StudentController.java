package com.myzghome.vertx.example.controller;

import com.alibaba.fastjson.JSON;
import com.myzghome.core.annotation.Loading;
import com.myzghome.core.annotation.Register;
import com.myzghome.vertx.example.module.Student;
import com.myzghome.vertx.example.service.StudentService;
import com.myzghome.vertx.example.tool.JsonResult;
import com.myzghome.vertx.mvc.annotation.controller.*;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

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
    public void getAll(RoutingContext routingContext) {
        routingContext.response().end(studentService.getAll());
    }

    @Get(path = ":id")
    public void getById(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id != null) {
            routingContext.response().end(studentService.getById(id));
            return;
        }
        routingContext.response().end(failResult);
    }

    @Post
    public void addStudent(RoutingContext routingContext) {
        Student student = Json.decodeValue(routingContext.getBodyAsString(), Student.class);
        if (student != null) {
            routingContext.response().end(studentService.add(student));
            return;
        }
        routingContext.response().end(failResult);
    }

    @Put(path = ":id")
    public void put(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id != null) {
            Student student = Json.decodeValue(routingContext.getBodyAsString(), Student.class);
            if (student != null) {
                routingContext.response().end(studentService.update(id, student));
                return;
            }
        }
        routingContext.response().end(failResult);
    }

    @Delete(path = ":id")
    public void delete(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id != null) {
            routingContext.response().end(studentService.delete(id));
            return;
        }
        routingContext.response().end(failResult);
    }
}
