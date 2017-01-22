package com.myzghome.core.bean.factory;

import com.myzghome.core.annotation.Loading;
import com.myzghome.core.annotation.Register;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述:
 */
@Register
public class ClassesTestBean {

    public String className;
    @Loading
    private StudentTestBean studentTestBean;

    public void test(){
        studentTestBean.hello();
    }

    public StudentTestBean getStudentTestBean() {
        return studentTestBean;
    }

    public void setStudentTestBean(StudentTestBean studentTestBean) {
        this.studentTestBean = studentTestBean;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
