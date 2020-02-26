package com.example.feigndemo.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class HeadersInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("user-agent", "wds");
    }
}
