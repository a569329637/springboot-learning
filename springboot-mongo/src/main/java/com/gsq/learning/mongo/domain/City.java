package com.gsq.learning.mongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@Document(collection = "city")
public class City implements Serializable {

    private static final long serialVersionUID = -2056654431811152723L;
    @Id
    private String id;
    @Field("province_id")
    private String provinceId;
    private String name;
    private List<String> desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", name='" + name + '\'' +
                ", desc=" + desc +
                '}';
    }
}
