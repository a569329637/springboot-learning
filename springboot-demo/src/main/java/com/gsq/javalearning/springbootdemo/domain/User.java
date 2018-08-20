package com.gsq.javalearning.springbootdemo.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @Entity
 * @Id
 * @GeneratedValue
 * 以上注解是 JPA 框架提供的，实体类和表建立映射关系的
 *
 * @Null 被注释的元素必须为 null
 * @NotNull 被注释的元素必须不为 null
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max, min) 被注释的元素的大小必须在指定的范围内
 * @Email 被注释的元素必须是电子邮箱地址
 * @Length 被注释的字符串的大小必须在指定的范围内
 * @NotEmpty 被注释的字符串的必须非空
 * @Range 被注释的元素必须在合适的范围内
 * Bean Validation 规范，运行时的数据验证框架，它是 JSR 303 规范，Hibernate Validator 实现了这套规范，并扩展了一些注解
 *
 * @author guishangquan
 * @date 2018/8/20
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "姓名不能为空")
    @Size(min = 2, max = 8, message = "姓名长度必须大于 2 且小于 20 字")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄大于 0")
    @Max(value = 300, message = "年龄不大于 300")
    private Integer age;

    @NotEmpty(message = "出生时间不能为空")
    private String birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
