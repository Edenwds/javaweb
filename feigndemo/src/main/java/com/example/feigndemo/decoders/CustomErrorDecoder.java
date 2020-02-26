package com.example.feigndemo.decoders;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        int code = response.status();
        if (code == 404) {
            throw new RetryableException(code, "Service Unavailable", response.request().httpMethod(), null, response.request());
        }
        return null;
    }
}
