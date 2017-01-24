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

    public List<Student> getAll() {
        return studentList;
    }

    public Student getById(String id) {
        Student student = get(id);
        if (student != null) {
            return student;
        }
        throw new RuntimeException("data not exists");
    }

    public void update(String id, Student s) {
        Student student = get(id);
        if (student != null) {
            student.setId(s.getId());
            student.setName(s.getName());
            student.setAge(s.getAge());
        }
    }

    public void add(Student student) {
        if (student != null) {
            studentList.add(student);
        }
    }

    public void delete(String id) {
        Student student = get(id);
        if (student != null) {
            studentList.remove(student);
        }
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
