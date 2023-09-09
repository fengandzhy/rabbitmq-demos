package org.frank.rabbitmq.exchange.fanout.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.fanout.common.CommonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * fanout类型的Exchange路由规则非常简单，它会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中。
 * 如果producer里面b绑定的queue是rabbit:mq02:queue:q02,那么在consumer里面 只能绑定rabbit:mq02:queue:q02 queue才能接受到消息
 * 如果不指定绑定的queue, 那么在consumer里面任意绑定的queue都能接收到消息
 * 对于发布者和订阅者这个模式来说, 一定要先开始consumer 在 开启 producer, 否则接受不到消息, 至少在本例中是如此.
 * */
public class Producer4Fanout {

    private static final String Exchange_Name="rabbit:mq02:exchange:e01";
    private static final String Queue_Name_02="rabbit:mq02:queue:q02";

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Queue_Name_02,true, false, false, null);
        channel.queueBind(Queue_Name_02,Exchange_Name,"");


        String message = "fanoutExchange-publish的消息";
        channel.basicPublish(Exchange_Name, "", null, message.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        CommonUtil.close(channel,connection);
    }
}
