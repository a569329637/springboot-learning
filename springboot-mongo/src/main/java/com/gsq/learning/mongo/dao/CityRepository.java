package com.gsq.learning.mongo.dao;

import com.gsq.learning.mongo.domain.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@Repository
public interface CityRepository extends MongoRepository<City, String> {
}
