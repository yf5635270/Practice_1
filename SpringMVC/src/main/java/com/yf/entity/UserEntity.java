package com.yf.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {


    private static final long serialVersionUID = 2557962943919342417L;
    private Integer id;

    private String name;

    private  Integer age;

    private Integer gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
