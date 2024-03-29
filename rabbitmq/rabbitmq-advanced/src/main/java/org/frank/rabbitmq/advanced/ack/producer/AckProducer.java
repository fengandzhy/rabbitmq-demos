package org.frank.rabbitmq.advanced.ack.producer;

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
 * channel.basicAck 表示签收消息, 消息已经被处理完
 * channel.basicNack 表示拒绝处理消息, 消息并没有被处理,  channel.basicNack(envelope.getDeliveryTag(),false,true); 
 * 第三个参数表示是否重回队列
 * 
 * 是的，channel.basicAck 方法用于表示消息已经成功消费完成。它是 RabbitMQ 中用于手动确认消息的一种方式。
 * 通常，这个方法由消费者（Consumer）调用，以通知 RabbitMQ 服务器可以将消息从队列中删除，因为它已经被成功处理。
 * */
public class AckProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        
        
        for(int i=0;i<10;i++){
            String message = "ack机制-publish message01  " + i;
            Map<String,Object> headers = new HashMap<>();
            headers.put("num",i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                                                .deliveryMode(2) // deliveryMode=2持久化消息：
                                                .contentEncoding("UTF-8")
                                                .headers(headers)
                                                .build();
            channel.basicPublish(Constant.ACK_EXCHANGE_NAME_01,
                    "mq:ack:routine:k.01",properties,message.getBytes());
        }        

        System.out.println("生产者发送消息成功---> ");

//        CommonUtil.close(channel,connection);
        
    }
}
