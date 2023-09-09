package org.frank.rabbitmq.exchange.topic.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.frank.rabbitmq.exchange.common.CommonUtil;
import org.frank.rabbitmq.exchange.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * topic类型的Exchange在匹配规则上进行了扩展，它与direct类型的Exchage相似，
 * 也是将消息路由到Binding Key与Routing Key相匹配的Queue中，但这里的匹配规则有些不同 它可以根据Routing Key 进行模糊匹配
 * */
public class Producer4Topic {

    private static final String Exchange_Name="rabbit:mq04:exchange:e01";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.TOPIC);
        String message = "topicExchange-publish我的消息";

        channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.*", null, message.getBytes("UTF-8"));
        //channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange.apple", null, message.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        CommonUtil.close(channel,connection);

    }
}
