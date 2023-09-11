package org.frank.rabbitmq.advanced.dlx.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.frank.rabbitmq.advanced.ack.consumer.MyAckConsumer;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DlxConsumer {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        
        // 正常队列的声明 主要在声明一个 queue 的时候多了一个 arguments 
        channel.exchangeDeclare("test_common_exchange",BuiltinExchangeType.TOPIC,true, false, null);        
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange"); // 这里的key 值是固定的, value 就是死信交换机的名称
        channel.queueDeclare("test_common_queue",true,false,false, arguments);        
        channel.queueBind("test_common_queue",
                "test_common_exchange","common_routing_key.*");

        //要进行死信队列的声明:
        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        Consumer consumer = new MyAckConsumer(channel);       
        
        /**
         * 这个地方不能加这条语句, 因为消息0 始终都会一个劲地在重回队尾
         * */
//        channel.basicQos(0,1,false);
        channel.basicConsume("test_common_queue", false, consumer);        
    }
}
