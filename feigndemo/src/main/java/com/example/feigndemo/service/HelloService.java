package com.example.feigndemo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.feigndemo.expander.DateExpander;
import com.example.feigndemo.pojo.FilePojo;
import com.example.feigndemo.pojo.ParamPojo;
import com.example.feigndemo.pojo.ResultPojo;
import feign.*;
import feign.form.FormData;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

public interface HelloService extends CommonService{
    @RequestLine("GET /test/hello/{name}")
    String hello(@Param("name") String name);

    @RequestLine("GET /test/hello2")
    ResultPojo hello2(@QueryMap Map<String, Object> querymap);

    @RequestLine("GET /test/hello2")
    ResultPojo hello3(@QueryMap ParamPojo paramPojo);

    @RequestLine("POST /test/post")
    @Headers("Content-Type: application/json")
    @Body("%7B\"username\": \"{username}\", \"password\": \"{password}\"%7D")
    String postTest(@Param("username") String username, @Param("password") String password);

    @RequestLine("GET /test/param/expander?since={date}")
    String paramExpanderTest(@Param(value = "date", expander = DateExpander.class)LocalDateTime time);

    @RequestLine("GET /test/json/decoder")
    ResultPojo jsonDecoderTest();

    @RequestLine("POST /test/json/encoder")
    @Headers("Content-Type: application/json")
    ResultPojo jsonEncoderTest(JSONObject content);

    @RequestLine(("GET /test/interceptor"))
    ResultPojo requestInterceptorTest();

    @RequestLine("GET /test/error/decoder")
    String errorDecoderTest();

    @RequestLine("GET /test/retry")
    String retryerTest();

    @RequestLine("POST /test/hello2")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResultPojo hello2Post(@Param("name") String name);

    @RequestLine("POST /test/hello2")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResultPojo hello2Post2(ParamPojo paramPojo);

    @RequestLine("POST /test/file")
    @Headers("Content-Type: multipart/form-data")
    String uploadFile(@Param("file") File file);

    @RequestLine("POST /test/byte-data")
    @Headers("Content-Type: multipart/form-data")
    String uploadByteData(@Param("bytedata") byte[] data);

    @RequestLine("POST /test/form-data")
    @Headers("Content-Type: multipart/form-data")
    String uploadByFormData(@Param("data") FormData formData);


    @RequestLine("POST /test/form-pojo")
    @Headers("Content-Type: multipart/form-data")
    String uploadByPojo(FilePojo filePojo);

}
