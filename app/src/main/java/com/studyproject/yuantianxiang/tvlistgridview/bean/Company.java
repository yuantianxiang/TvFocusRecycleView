package com.studyproject.yuantianxiang.tvlistgridview.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司
 * Created by yuantianxiang on 2018/3/5.
 */

public class Company {
    private String name;
    private List<Person> personList=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

}
