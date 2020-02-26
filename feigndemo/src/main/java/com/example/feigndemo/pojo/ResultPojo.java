package com.example.feigndemo.pojo;

public class ResultPojo {

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultPojo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
