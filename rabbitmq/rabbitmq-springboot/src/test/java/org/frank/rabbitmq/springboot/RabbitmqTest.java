package org.frank.rabbitmq.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Environment env;

    @Test
    public void testSend() {
        for(int i=0;i<5;i++){
            template.convertAndSend(env.getProperty("mq.config.exchange"), "order.new","新订单");
        }
    }
    
    @Test
    public void testConfirmCallback() {
        template.setConfirmCallback(((correlationData, ack, cause) -> {
            System.out.println("correlationData="+correlationData);
            System.out.println("ack="+ack);
            System.out.println("cause="+cause);
            
            if(ack){
                System.out.println("消息发送成功, 数据库状态已经更新为发送成功");
            }else{
                System.out.println("消息发送失败, 数据库状态已经更新为发送失败");
            }
        }));
        template.convertAndSend(env.getProperty("mq.config.exchange"), "order.new","新订单");
    }
    
    @Test
    public void testReturnCallback() {
        // 这个方法只有在消息失败的时候才会去调用, 从exchange 到 queue 失败的时候会调用
        template.setReturnsCallback(returned -> {
            System.out.println("returned="+returned.toString());
        });
        // 这里模拟一条失败消息, 找不到 routingKey 的 
        template.convertAndSend(env.getProperty("mq.config.exchange"), "order1.new","从ReturnCallback那里来的新订单");
    }    
    
}
