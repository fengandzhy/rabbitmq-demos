package org.frank.rabbitmq.exchange.fanout.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.frank.rabbitmq.exchange.common.CommonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Fanout {

    private static final String Exchange_Name="rabbit:mq02:exchange:e01";
    private static final String Queue_Name_01="rabbit:mq02:queue:q01";

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Queue_Name_01, false, false, false, null);
        channel.queueBind(Queue_Name_01,Exchange_Name,"");

        Consumer consumer = CommonUtil.createConsumer(channel);

        channel.basicConsume(Queue_Name_01, true, consumer);

    }
}
