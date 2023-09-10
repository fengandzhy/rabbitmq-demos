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
            template.convertAndSend(env.getProperty("mq.config.exchange"), "order.new","新订单"+i);
        }
    }
    
    /**
     * ConfirmCallback是在消息发给交换机时被回调，通过这个回调函数我们能知道发送的消息内容，路由键，交换机名称，是否投递成功等内容；
     * */
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
    
    /**
     * ReturnCallback 则是在交换机路由不到队列的时候被调用。它通过这个回调函数将你的消息退还给你，让你自行处理
     * 也可以是发送者没有找到交换机时, 它通过这个回调函数将你的消息退还给你，让你自行处理
     * */
    @Test
    public void testReturnCallback() {
        
        template.setReturnsCallback(returned -> {
            System.out.println("消息投递不成功, 被退回.");
            System.out.println("returned="+returned.toString());
        });
        // 这里模拟一条失败消息, 找不到 routingKey 的 
        template.convertAndSend(env.getProperty("mq.config.exchange"), "order1.new","从ReturnCallback那里来的新订单");
    }    
    
}
