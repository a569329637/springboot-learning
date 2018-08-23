package com.gsq.learning.mongo.dao;

import com.gsq.learning.mongo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User getUser(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public void update(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = Update.update("age", user.getAge())
                .set("name", user.getName())
                .set("birthday", user.getBirthday());
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void insert(User user) {
        mongoTemplate.insert(user);
    }

    @Override
    public void insertAll(List<User> users) {
        mongoTemplate.insertAll(users);
    }

    @Override
    public void remove(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        // 这里要指定类型
        mongoTemplate.remove(query, User.class);
    }

    @Override
    public List<User> findByPage(Pageable pageable) {
        Query query = new Query();
        return mongoTemplate.find(query.with(pageable), User.class);
    }
}
