package com.example.cloudalibaba.sentinel.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class TestController {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("localhost:8082/hello?str=123")
                .method("GET", null)
                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void test01(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        try {
            Random random = new Random();
            while(true){
                int i = random.nextInt(10);
                if(i > 5){
                    Request request = new Request.Builder()
                            .url("http://localhost:8082/hello?str="+i)
                            .method("GET", null)
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println(response.body().string());
                }
                Thread.sleep(random.nextInt(50));
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
