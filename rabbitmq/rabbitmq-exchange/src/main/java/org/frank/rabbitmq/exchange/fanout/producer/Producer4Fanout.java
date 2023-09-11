package org.frank.rabbitmq.exchange.fanout.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * fanout类型的Exchange路由规则非常简单，它会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中。
 * 如果producer里面b绑定的queue是rabbit:mq02:queue:q02,那么在consumer里面 只能绑定rabbit:mq02:queue:q02 queue才能接受到消息
 * 如果不指定绑定的queue, 那么在consumer里面任意绑定的queue都能接收到消息
 * 对于发布者和订阅者这个模式来说, 一定要先开始consumer 在 开启 producer, 否则接受不到消息, 至少在本例中是如此.
 * 
 * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有Queue上。
 * 1.可以理解为路由表的模式
 * 2.这种模式不需要RouteKey
 * 3.这种模式需要提前将Exchange与Queue进行绑定，一个Exchange可以绑定多个Queue，一个Queue可以同多个Exchange进行绑定。
 * 4.如果接受到消息的Exchange没有与任何Queue绑定，则消息会被抛弃。
 *  
 *  
 * 打个比方说, 某个Exchange 有两个 queue, 一个routing key 是 "a" 另一个routing key 是空字符串, 如果这个exchange 是fanout的话,
 * 它所有的消息都会发送到这两个 queue里面. 
 * 
 * */
public class Producer4Fanout {

    private static final String Exchange_Name="rabbit:mq02:exchange:e01";
    private static final String Queue_Name_02="rabbit:mq02:queue:q02";

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Queue_Name_02,false, false, false, null);
        channel.queueBind(Queue_Name_02,Exchange_Name,"");


        String message = "fanoutExchange-publish的消息";
        channel.basicPublish(Exchange_Name, "", null, message.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        CommonUtil.close(channel,connection);
    }
}
