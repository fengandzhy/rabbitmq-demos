package org.zhouhy.rabbitmq.fanout;

import com.rabbitmq.client.*;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Fanout {

    private static final String Exchange_Name="rabbit:mq02:exchange:e01";
    private static final String Queue_Name_01="rabbit:mq02:queue:q01";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Queue_Name_01, true, false, false, null);
        channel.queueBind(Queue_Name_01,Exchange_Name,"");

        Consumer consumer = CommUtil.createConsumer(channel);

        channel.basicConsume(Queue_Name_01, true, consumer);

    }
}
