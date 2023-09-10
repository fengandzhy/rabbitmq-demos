package org.frank.rabbitmq.advanced.retu.listener.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReturnReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.RETURN_EXCHANGE_NAME_01,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(Constant.RETURN_QUEUE_NAME_01,false,false,false,null);
        channel.queueBind(Constant.RETURN_QUEUE_NAME_01,
                Constant.RETURN_EXCHANGE_NAME_01,Constant.RETURN_ROUTINE_KEY_01);

        Consumer consumer = CommonUtil.createConsumer(channel);

        channel.basicConsume(Constant.RETURN_QUEUE_NAME_01, true, consumer);
    }
}
