package org.zhouhy.rabbitmq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer4Fanout {

    private static final String Exchange_Name="rabbit:mq02:exchange:e01";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);
        String message = "fanoutExchange-publish的消息";
        channel.basicPublish(Exchange_Name, "", null, message.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);
    }
}
