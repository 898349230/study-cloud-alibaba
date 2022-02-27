package com.example.cloudalibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.cloudalibaba.configuration.GloableBlockHandler;
import com.example.cloudalibaba.configuration.GloableFallbackClass;
import com.example.cloudalibaba.constants.SentinelResourceConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {

    /**
     * 默认使用同一个类中的降级方法
     * @param id
     * @return
     */
    @SentinelResource(value = SentinelResourceConstants.RESOURCE_GET_ID, blockHandler = "getIdBlockHandler", fallback = "getIdExceptionHandler")
    @RequestMapping("/getId")
    public String getId(String id){
        Integer.parseInt(id);
        return id;
    }

    /**
     * 限流处理 必须是public方法
     * @param id
     * @param ex BlockException参数 可加可不加
     * @return
     */
    public String getIdBlockHandler(String id, BlockException ex){
        ex.printStackTrace();
        return id + " Block...";
    }

    /**
     * 异常处理， 必须是public方法
     * @param id
     * @param throwable Throwable参数 可加可不加
     * @return
     */
    public String getIdExceptionHandler(String id, Throwable throwable){
        throwable.printStackTrace();
        return id + " Exception...";
    }

    /**
     * 指定异常处理类
     * @param goodId
     * @return
     */
    @SentinelResource(value = SentinelResourceConstants.RESOURCE_GET_GOOD,
            blockHandlerClass = {GloableBlockHandler.class} ,blockHandler = "gloableDefaultBlock",
            fallbackClass = {GloableFallbackClass.class},fallback = "gloableDefaultException")
    @RequestMapping("/getGood")
    public String getGood(String goodId){
        Integer.parseInt(goodId);
        return goodId;
    }

    /**
     * 参数限流
     * @param id
     * @param name
     * @return
     */
    @SentinelResource(value = SentinelResourceConstants.RESOURCE_GET_GOOD_BY_ID_OR_NAME,
            blockHandlerClass = {GloableBlockHandler.class} ,blockHandler = "gloableDefaultBlock2",
            fallbackClass = {GloableFallbackClass.class},fallback = "gloableDefaultException2")
    @RequestMapping("/getGoodByIdOrName")
    public String getGoodByIdOrName(Integer id, String name){

        return "id = " + id +" name = " + name;
    }
}
