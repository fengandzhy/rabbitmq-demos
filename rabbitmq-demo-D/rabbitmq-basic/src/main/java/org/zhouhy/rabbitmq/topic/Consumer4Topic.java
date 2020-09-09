package org.zhouhy.rabbitmq.topic;

import com.rabbitmq.client.*;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Topic {

    private static final String Exchange_Name="rabbit:mq04:exchange:e01";

    private static final String Queue_Name_01="rabbit:mq04:queue:q01";
    private static final String Routing_Key_01="rabbit:mq04:routing:key:r.*";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(Queue_Name_01, true, false, false, null);
        channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);

        Consumer consumer = CommUtil.createConsumer(channel);

        channel.basicConsume(Queue_Name_01, true, consumer);
    }
}
