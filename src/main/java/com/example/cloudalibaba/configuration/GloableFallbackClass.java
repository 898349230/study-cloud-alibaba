package com.example.cloudalibaba.configuration;

public class GloableFallbackClass {

    /**
     * 给其他类使用时， 当前类必须是 static 方法
     * @param id
     * @param throwable
     * @return
     */
    public static String gloableDefaultException(String id, Throwable throwable){
        throwable.printStackTrace();
        return id + " gloableDefaultException...";
    }

    public static String gloableDefaultException2(Integer id, String name, Throwable throwable){
        throwable.printStackTrace();
        return "id = " + id +" name = " + name + " gloableDefaultException...";
    }
}
