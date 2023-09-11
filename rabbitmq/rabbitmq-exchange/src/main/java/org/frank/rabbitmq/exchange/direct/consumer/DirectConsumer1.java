package org.frank.rabbitmq.exchange.direct.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;
import org.frank.rabbitmq.exchange.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectConsumer1 {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Constant.DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        System.out.println(queueName);

        channel.queueBind(queueName,Constant.DIRECT_EXCHANGE_NAME,Constant.ERROR_ROUTING_KEY);
        channel.basicConsume(queueName,false,CommonUtil.createConsumer(channel));
    }
}
