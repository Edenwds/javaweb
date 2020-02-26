package com.example.feigndemo;

import com.alibaba.fastjson.JSONObject;
import com.example.feigndemo.decoders.CustomErrorDecoder;
import com.example.feigndemo.interceptor.HeadersInterceptor;
import com.example.feigndemo.pojo.ParamPojo;
import com.example.feigndemo.pojo.ResultPojo;
import com.example.feigndemo.retryer.MyRetryer;
import com.example.feigndemo.service.HelloService;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class FeigndemoApplicationTests {

	HelloService service = Feign.builder()
			.logger(new Slf4jLogger())
			.logLevel(Logger.Level.FULL)
			.client(new OkHttpClient())
			.encoder(new JacksonEncoder())
			.decoder(new JacksonDecoder())
			.requestInterceptor(new HeadersInterceptor())
			.errorDecoder(new CustomErrorDecoder())
			.retryer(new MyRetryer(5))
			.exceptionPropagationPolicy(ExceptionPropagationPolicy.UNWRAP)
			.target(HelloService.class, "http://localhost:8080/");

	@Test
	void contextLoads() {
	}

	@Test
	public void feignRESTTes() {
		ParamPojo paramPojo = new ParamPojo("peter");
		System.out.println(service.hello3(paramPojo).toString());
	}

	@Test
	public void feignQueryMapTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "tom");
		System.out.println(service.hello2(map));
	}

	@Test
	public void feignPojoTest() {
		ParamPojo paramPojo = new ParamPojo("peter");
		System.out.println(service.hello3(paramPojo));
	}

	@Test
	public void postTest() {
		String res = service.postTest("admin", "123");
		System.out.println(res);
	}

	@Test
	public void paramExpanderTest() {
		String res = service.paramExpanderTest(LocalDateTime.now());
		Assert.assertEquals("success", res);
	}

	@Test
	public void jsonDecoderTest() {
		ResultPojo res = service.jsonDecoderTest();
		System.out.println(res.toString());
	}

	@Test
	public void jsonEncoderTest() {
		JSONObject param = new JSONObject();
		param.put("name", "admin");
		param.put("password", "1234");
		param.put("roles", Arrays.asList(1, 2));
		ResultPojo res = service.jsonEncoderTest(param);
		System.out.println(res.toString());
	}

	@Test
	public void commonServiceTest() {
		ResultPojo res = service.common("tom");
		System.out.println(res.toString());
	}

	@Test
	public void interceptorTest() {
		ResultPojo res = service.requestInterceptorTest();
		System.out.println(res.toString());
	}

	@Test
	public void errorDecoderTest() {
		service.errorDecoderTest();
	}
	
	@Test
	public void retryerTest() {
		service.retryerTest();
	}
}
