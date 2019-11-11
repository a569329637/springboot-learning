package com.gsq.learning.webflux.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 路由器类
 *
 * @author guishangquan
 * @date 2019-11-11
 */
@Configuration
public class HelloWebFlux {

    @Bean
    public RouterFunction<ServerResponse> hello(HelloHandler helloHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                helloHandler::hello
        );
    }

}
