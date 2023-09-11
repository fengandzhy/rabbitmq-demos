package org.frank.rabbitmq.springboot.ttl.controller;


import org.frank.rabbitmq.springboot.ttl.config.MessageQueueConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/

@RestController
@RequestMapping("/api/admin/merchant")
public class MerchantAccountController {
    
    private RabbitTemplate rabbitTemplate;

    @GetMapping("check")
    public Object check(){

        //修改数据库的商家账号状态  TODO

        rabbitTemplate.convertAndSend(MessageQueueConfig.NEW_MERCHANT_EXCHANGE,MessageQueueConfig.NEW_MERCHANT_ROUTING_KEY,"商家账号通过审核");

        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","账号审核通过，请10秒内上传1个商品");
        return map;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
