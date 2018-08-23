package com.gsq.learning.mongo.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
public class User implements Serializable {

    private static final long serialVersionUID = -704506918766056654L;
    @Id
    private String id;
    private Integer age;
    private String name;
    private String birthday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
