package com.gsq.learning.mybatis.dao;

import com.gsq.learning.mybatis.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@Mapper
public interface UserDao {

    @Select("select * from user where name=#{name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "age", column = "age"),
            @Result(property = "name", column = "name"),
            @Result(property = "birthday", column = "birthday")
    })
    User findByName(@Param("name") String name);

    @Select("select * from user")
    List<User> findAll();
}
