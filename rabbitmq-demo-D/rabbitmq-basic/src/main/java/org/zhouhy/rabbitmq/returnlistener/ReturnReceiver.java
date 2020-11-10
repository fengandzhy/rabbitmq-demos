package org.zhouhy.rabbitmq.returnlistener;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReturnReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RabbitMQConstants.RETURN_EXCHANGE_NAME_01,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(RabbitMQConstants.RETURN_QUEUE_NAME_01,true,false,false,null);
        channel.queueBind(RabbitMQConstants.RETURN_QUEUE_NAME_01,
                RabbitMQConstants.RETURN_EXCHANGE_NAME_01,RabbitMQConstants.RETURN_ROUTINE_KEY_01);

        Consumer consumer = CommUtil.createConsumer(channel);

        channel.basicConsume(RabbitMQConstants.RETURN_QUEUE_NAME_01, true, consumer);
    }
}
