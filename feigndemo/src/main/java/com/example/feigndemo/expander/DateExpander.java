package com.example.feigndemo.expander;

import feign.Param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateExpander implements Param.Expander {

    @Override
    public String expand(Object o) {
        LocalDateTime date = (LocalDateTime) o;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
