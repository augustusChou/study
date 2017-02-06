package com.myzghome.core.bean.factory;

import com.myzghome.core.annotation.Register;

/**
 * 作者：周广
 * 创建时间：2017/2/6 0006
 * 必要描述:
 */
@Register
public class CourseTestBean {
    private int num;
    private StudentTestBean studentTestBean;
    private ClassesTestBean classesTestBean;

    public CourseTestBean(StudentTestBean studentTestBean, ClassesTestBean classesTestBean) {
        this.studentTestBean = studentTestBean;
        this.classesTestBean = classesTestBean;
    }


    public StudentTestBean getStudentTestBean() {
        return studentTestBean;
    }

    public void setStudentTestBean(StudentTestBean studentTestBean) {
        this.studentTestBean = studentTestBean;
    }

    public ClassesTestBean getClassesTestBean() {
        return classesTestBean;
    }

    public void setClassesTestBean(ClassesTestBean classesTestBean) {
        this.classesTestBean = classesTestBean;
    }
}
