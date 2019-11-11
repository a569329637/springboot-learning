package com.gsq.learning.webflux.crud.controller;

import com.gsq.learning.webflux.crud.domain.City;
import com.gsq.learning.webflux.crud.handler.CityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://www.bysocket.com/technique/2328.html
 *
 * @author guishangquan
 * @date 2019-11-11
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityHandler cityHandler;

    @GetMapping("/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        return cityHandler.findCityById(id);
    }

    @GetMapping()
    public Flux<City> finAllCity() {
        return cityHandler.findAllCity();
    }

    @PostMapping()
    public Mono<Long> saveCity(@RequestBody City city) {
        return cityHandler.save(city);
    }

    @PutMapping()
    public Mono<Long> modifyCity(@RequestBody City city) {
        return cityHandler.modifyCity(city);
    }

    @DeleteMapping("/{id}")
    public Mono<Long> deleteCity(@PathVariable Long id) {
        return cityHandler.deleteCity(id);
    }

}
