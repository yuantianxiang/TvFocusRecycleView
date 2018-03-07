package com.studyproject.yuantianxiang.tvlistgridview.bean;

/**
 * Created by yuantianxiang on 2018/2/22.
 */

public class Person {
    private String  headImage;
    private String name;
    private int age;

    public Person(String headImage, String name, int age) {
        this.headImage = headImage;
        this.name = name;
        this.age = age;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
