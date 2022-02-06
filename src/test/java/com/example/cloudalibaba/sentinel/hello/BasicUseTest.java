package com.example.cloudalibaba.sentinel.hello;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BasicUseTest {

    private static String PROCTED_RESOURCE = "helloApp";

    /**
     * try with resource 方式
     */
    @Test
    public void test01(){
        initFlowRules();
        int i = 0;
        while (true){
            // 保护资源
            try(Entry entry = SphU.entry(PROCTED_RESOURCE)){
                System.out.println("*************** " + i++);
                Thread.sleep(50);
            } catch (BlockException e) {
                System.out.println("触发流控");
//                e.printStackTrace();
//                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Before
    public void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setLimitApp("fooApp");
        rule1.setResource(PROCTED_RESOURCE);
        // qps
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // qps 20
        rule1.setCount(20);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

}
