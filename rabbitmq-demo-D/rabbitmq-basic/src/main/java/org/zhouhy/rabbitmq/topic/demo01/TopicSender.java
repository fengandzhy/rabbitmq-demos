package org.zhouhy.rabbitmq.topic.demo01;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicSender {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        String message1 = "topicExchange-publish我的消息1";
        String message2 = "topicExchange-publish我的消息2";
        
        channel.exchangeDeclare(RabbitMQConstants.TOPIC_EXCHANGE_NAME_01,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(RabbitMQConstants.TOPIC_QUEUE_NAME_01,true,false,false,null);
        channel.queueBind(RabbitMQConstants.TOPIC_QUEUE_NAME_01,RabbitMQConstants.TOPIC_EXCHANGE_NAME_01,RabbitMQConstants.TOPIC_ROUTINE_KEY_01);
        
        channel.basicPublish(RabbitMQConstants.TOPIC_EXCHANGE_NAME_01,"mq:topic:routine:k.1",null,message1.getBytes());
        channel.basicPublish(RabbitMQConstants.TOPIC_EXCHANGE_NAME_01,"mq:topic:routine:k.2",null,message2.getBytes());

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);
    }
}
