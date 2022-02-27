package com.example.cloudalibaba.configuration;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class GloableBlockHandler {

    /**
     * 给其他类使用时， 当前类必须是 static 方法
     * @param id
     * @param exception
     * @return
     */
    public static String gloableDefaultBlock(String id, BlockException exception){
        exception.printStackTrace();
        return id + " gloableDefaultBlock...";
    }

    public static String gloableDefaultBlock2(Integer id, String name, BlockException exception){
        exception.printStackTrace();
        return "id = " + id +" name = " + name + " gloableDefaultBlock...";
    }
}
