package com.example.feigndemo.service;

import com.example.feigndemo.pojo.ResultPojo;
import feign.Param;
import feign.RequestLine;

public interface CommonService {

    @RequestLine("GET /test/hello2?name={name}")
    ResultPojo common(@Param("name") String name);
}
