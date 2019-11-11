package com.gsq.learning.webflux.crud.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guishangquan
 * @date 2019-11-11
 */
@Setter
@Getter
public class City {
    /**
     * 城市编码
     */
    private Long id;
    /**
     * 省份编码
     */
    private Long provinceId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 描述
     */
    private String description;

}
