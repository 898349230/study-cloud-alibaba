package com.example.cloudalibaba.configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.example.cloudalibaba.constants.SentinelResourceConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * sentinel 配置
 */
@Configuration
public class SentinelAspectConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceConfig(){

        // 接口限流
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(SentinelResourceConstants.RESOURCE_GET_ID);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setCount(1);
        rules.add(rule1);

        FlowRule rule2 = new FlowRule();
        rule2.setResource(SentinelResourceConstants.RESOURCE_GET_GOOD);
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setCount(2);
        rules.add(rule2);

        FlowRuleManager.loadRules(rules);

        // 参数限流
        ParamFlowRule paramFlowRule = new ParamFlowRule();
        paramFlowRule.setResource(SentinelResourceConstants.RESOURCE_GET_GOOD_BY_ID_OR_NAME);
        paramFlowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 第二个参数进行限流(只要第二个参数不是null，就会触发限流)
        paramFlowRule.setParamIdx(1);
        paramFlowRule.setCount(2);

        // 第二个参数是 zhangsan 时， 限流值为 5
        List<ParamFlowItem> itemList = new ArrayList<>();
        ParamFlowItem item = new ParamFlowItem();
        item.setClassType(String.class.getName()).setCount(5).setObject("zhangsan");
        itemList.add(item);
        paramFlowRule.setParamFlowItemList(itemList);
        ParamFlowRuleManager.loadRules(CollectionUtils.arrayToList(new ParamFlowRule[]{paramFlowRule}));

        System.out.println("SentinelResourceAspect config...");
        return new SentinelResourceAspect();
    }
}
