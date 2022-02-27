package com.example.cloudalibaba.sentinel.parameterflow;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 热点参数限流
 */
public class ParamFlowTest {
    private static String resourceName = "methodA";
    private static String paramA = "a";
    private static String paramB = "b";

    public static void main(String[] args) throws InterruptedException {
        initParamFlowRules();
        boolean stop = false;
        int block = 0;
        int pass = 0;
        long l = System.currentTimeMillis();
        while (!stop) {
            Entry entry = null;
            try {
                entry = SphU.entry(resourceName, EntryType.IN, 1,  null, paramA);
                ++pass;
                // Add pass for parameter.
            } catch (BlockException e) {
                ++block;
                // block.incrementAndGet();
            } catch (Exception ex) {
                // biz exception
            } finally {
                // total.incrementAndGet();
                if (entry != null) {
                    entry.exit(1, null, paramA);
                }
            }
            System.out.println("pass = " + pass + " block = " + block);
            TimeUnit.MILLISECONDS.sleep(10);
            if(System.currentTimeMillis() - l > 1000) stop = true;
        }
    }

    private static void initParamFlowRules() {
        // QPS mode, threshold is 5 for every frequent "hot spot" parameter in index 0 (the first arg).
        ParamFlowRule rule = new ParamFlowRule(resourceName)
                // 设置第 0 个参数限流策略（如果有第0个参数就会限流）
                .setParamIdx(0)
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                //.setDurationInSec(3)
                //.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
                //.setMaxQueueingTimeMs(600)
                .setCount(5);

        // We can set threshold count for specific parameter value individually.
        // Here we add an exception item. That means: QPS threshold of entries with parameter `PARAM_B` (type: int)
        // in index 0 will be 10, rather than the global threshold (5).
        // 当第0个参数是 abc 时，限流阈值为 10
        ParamFlowItem item = new ParamFlowItem().setObject("abc")
                .setClassType(String.class.getName())
                .setCount(10);
        rule.setParamFlowItemList(Collections.singletonList(item));

        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }
}
