package org.zhouhy.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer4Topic {

    private static final String Exchange_Name="rabbit:mq04:exchange:e01";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        String message = "topicExchange-publish我的消息";

        channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange", null, message.getBytes("UTF-8"));
        channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange.apple", null, message.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);

    }
}
