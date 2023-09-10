package org.frank.rabbitmq.advanced.confirm.consumer;

import com.rabbitmq.client.*;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.CONFIRM_EXCHANGE_NAME,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(Constant.CONFIRM_QUEUE_NAME_01,false,false,false,null);
        
        channel.queueBind(Constant.CONFIRM_QUEUE_NAME_01,Constant.CONFIRM_EXCHANGE_NAME,Constant.CONFIRM_ROUTING_KEY_01);

        Consumer consumer = CommonUtil.createConsumer(channel);
        
        channel.basicConsume(Constant.CONFIRM_QUEUE_NAME_01, true, consumer);        
    }
}
