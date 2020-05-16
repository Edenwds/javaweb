package com.example.feigndemo;

import com.alibaba.fastjson.JSONObject;
import com.example.feigndemo.decoders.CustomErrorDecoder;
import com.example.feigndemo.interceptor.HeadersInterceptor;
import com.example.feigndemo.pojo.FilePojo;
import com.example.feigndemo.pojo.ParamPojo;
import com.example.feigndemo.pojo.ResultPojo;
import com.example.feigndemo.retryer.MyRetryer;
import com.example.feigndemo.service.HelloService;
import feign.*;
import feign.form.FormData;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
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
//			.encoder(new JacksonEncoder())
			.encoder(new FormEncoder(new JacksonEncoder()))
//			.decoder(new JacksonDecoder())
			.requestInterceptor(new HeadersInterceptor())
			.errorDecoder(new CustomErrorDecoder())
			.retryer(new MyRetryer(5))
			.exceptionPropagationPolicy(ExceptionPropagationPolicy.UNWRAP)
			.target(HelloService.class, "http://localhost:8080/");

	@Test
	void contextLoads() {
	}

	@Test
	public void feignRESTTest() {
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

	@Test
	public void postFormTest() {
		System.out.println(service.hello2Post("tom"));
	}

	@Test
	public void postForm2Test() {
		ParamPojo paramPojo = new ParamPojo("tom");
		System.out.println(service.hello2Post2(paramPojo));
	}

	@Test
	public void postFileTest() {
		String path = "/Users/xx/Desktop/net.png";
		File file = new File(path);
		String res = service.uploadFile(file);
		System.out.println(res);

	}

	@Test
	public void postByteDataTest() {
		String msg = "hello world";
		String res = service.uploadByteData(msg.getBytes());
		System.out.println(res);
	}

	@Test
	public void postByFormDataTest() throws IOException {
		String path = "/Users/xx/Desktop/net.png";
		File file = new File(path);
		InputStream inputStream = new FileInputStream(file);
		byte[] data = new byte[1024 * 1024];
		inputStream.read(data);
		FormData formData = new FormData("image/png", "net.png", data);
		service.uploadByFormData(formData);
	}

	@Test
	public void postByPojoTst() {
		String path = "/Users/xx/Desktop/net.png";
		File file = new File(path);
		FilePojo pojo = new FilePojo("1", file);
		System.out.println(service.uploadByPojo(pojo));
	}
}
