package org.frank.rabbitmq.advanced.dlx.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列, 首先要创建一个死信交换机 以及和这个死信交换机绑定的 queue 
 * channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
 * channel.queueDeclare("dlx.queue", true, false, false, null);
 * channel.queueBind("dlx.queue", "dlx.exchange", "#");
 * 
 * 其次在创建正常队列的时候, 加入一个arguments, 这个arguments 是一个 map 
 * channel.queueDeclare("test_common_queue",true,false,false, arguments); 
 * 
 * 
 * */
public class DlxProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();      
        
        String message = "dxl-publish message01  ";           
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                                            .deliveryMode(2) // deliveryMode=2持久化消息：
                                            .contentEncoding("UTF-8")
                                            .expiration("10000") // 如果消息10秒钟内不被消费, 就会超过TTL 
                                            .build();
        channel.basicPublish("test_common_exchange",
                "common_routing_key.a",properties,message.getBytes());
        System.out.println("生产者发送消息成功---> ");        
    }
}
