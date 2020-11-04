package org.zhouhy.rabbitmq.fanout.demo01;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutConsumer1 {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(RabbitMQConstants.FANOUT_QUEUE_NAME_01,true,false,false,null);
        channel.queueDeclare(RabbitMQConstants.FANOUT_QUEUE_NAME_02,true,false,false,null);

        channel.queueBind(RabbitMQConstants.FANOUT_QUEUE_NAME_01,RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,"");
        channel.queueBind(RabbitMQConstants.FANOUT_QUEUE_NAME_02,RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,"");

        Consumer consumer = CommUtil.createConsumer(channel);

        channel.basicConsume(RabbitMQConstants.FANOUT_QUEUE_NAME_01, true, consumer);
        channel.basicConsume(RabbitMQConstants.FANOUT_QUEUE_NAME_02, true, consumer);
    }
}
