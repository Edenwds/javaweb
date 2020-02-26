package com.example.feigndemo.retryer;

import feign.RetryableException;
import feign.Retryer;

public class MyRetryer implements Retryer {

    private final int maxAttempts;
    int attempt;

    public MyRetryer(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (this.attempt++ >= this.maxAttempts) {
            throw e;
        } else {
            System.out.println("重试 " + attempt + " 次");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Retryer clone() {
        return new MyRetryer(5);
    }
}
