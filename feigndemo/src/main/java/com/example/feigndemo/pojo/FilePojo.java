package com.example.feigndemo.pojo;

import feign.form.FormProperty;

import java.io.File;

public class FilePojo {

    @FormProperty("id")
    String id;

    @FormProperty("filename")
    File file;

    public FilePojo() {
    }

    public FilePojo(String id, File file) {
        this.id = id;
        this.file = file;
    }
}
