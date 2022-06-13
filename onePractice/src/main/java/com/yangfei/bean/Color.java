package com.yangfei.bean;

public class Color {

    private String id;
    private String value;

    public Color() {
        System.out.println("color.......constructor");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
