package com.gsq.learning.mongo;

import com.gsq.learning.mongo.dao.CityRepository;
import com.gsq.learning.mongo.domain.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guishangquan
 * @date 2018/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests2 {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void testInsert() {
        City city = new City();
        city.setProvinceId("987654321");
        city.setName("深圳");
        List<String> desc = new ArrayList<>();
        desc.add("desc1");
        desc.add("desc2");
        city.setDesc(desc);
        cityRepository.insert(city);
    }

    @Test
    public void testUpdate() {
        City query = new City();
        query.setName("深圳");
        Example example = Example.of(query);
        City city = (City) cityRepository.findOne(example).get();
        city.setProvinceId("123456789");
        city.getDesc().add("desc3");
        cityRepository.save(city);
    }

    @Test
    public void testFindAll() {
        List<City> cites = cityRepository.findAll();
        System.out.println("cites = " + cites);
    }

    @Test
    public void testRemove() {
        cityRepository.deleteAll();
    }
}
