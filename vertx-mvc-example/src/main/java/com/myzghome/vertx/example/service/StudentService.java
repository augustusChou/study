package com.myzghome.vertx.example.service;

import com.alibaba.fastjson.JSON;
import com.myzghome.core.annotation.Register;
import com.myzghome.vertx.example.module.Student;
import com.myzghome.vertx.example.tool.JsonResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：周广
 * 创建时间：2017/1/23 0023
 * 必要描述:
 */
@Register
public class StudentService {

    private static final String successResult = JSON.toJSONString(JsonResult.SUCESS);
    private static final String failResult = JSON.toJSONString(JsonResult.FAIL());
    private List<Student> studentList = new ArrayList<>();

    public String getAll() {
        return JSON.toJSONString(studentList);
    }

    public String getById(String id) {
        Student student = get(id);
        if (student != null) {
            return JSON.toJSONString(student);
        }
        return failResult;
    }

    public String update(String id, Student s) {
        Student student = get(id);
        if (student != null) {
            student.setId(s.getId());
            student.setName(s.getName());
            student.setAge(s.getAge());
            return successResult;
        }
        return failResult;
    }

    public String add(Student student) {
        if (student != null) {
            studentList.add(student);
            return successResult;
        }
        return failResult;
    }

    public String delete(String id) {
        Student student = get(id);
        if (student != null) {
            studentList.remove(student);
        }
        return successResult;
    }


    private Student get(String id) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }
}
