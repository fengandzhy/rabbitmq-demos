package org.frank.rabbitmq.exchange.direct.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * Queue（队列）是RabbitMQ的内部对象，用于存储消息.
 * Exchange（交换器）生产者将消息发送到Exchange（交换器，下图中的X），由Exchange将消息路由到一个或多个Queue中（或者丢弃）.
 *
 * Binding RabbitMQ中通过Binding将Exchange与Queue关联起来，这样RabbitMQ就知道如何正确地将消息路由到指定的Queue了
 *
 * Routing Key 生产者在将消息发送给Exchange的时候，一般会指定一个Routing Key，来指定这个消息的路由规则，
 * 而这个Routing Key需要与Exchange Type及Binding key联合使用才能最终生效
 *
 * direct类型的Exchange路由规则也很简单，它会把消息路由到那些Binding key与Routing key完全匹配的Queue中
 * 也就是说它只认Queue_Name_02 和 Routing key 完全匹配
 * 但是如果要修改这个Queue_Name_02 和 Routing key 的匹配关系，比方说 Queue_Name_02 改成Queue_Name_01 那么一定要在页面中解绑unbind
 * 单纯的修改代码是不起作用的
 *
 * */
public class Producer4Direct {

    private static final String Exchange_Name="rabbit:mq03:exchange:e01";

    private static final String Queue_Name_01="rabbit:mq03:queue:q01";
    private static final String Queue_Name_02="rabbit:mq03:queue:q02";

    private static final String Routing_Key_01="rabbit:mq03:routing:key:r01";
    private static final String Routing_Key_02="rabbit:mq03:routing:key:r02";
    private static final String Routing_Key_03="rabbit:mq03:routing:key:r03";

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.DIRECT);
        channel.queueDeclare(Queue_Name_01,true, false, false, null);
        channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);

        channel.queueDeclare(Queue_Name_02, true, false, false, null);
        channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_02);
        channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_03);

        String message01 = "directExchange-publish我的消息-r01";
        String message02 = "directExchange-publish我的消息-r02";
        String message03 = "directExchange-publish我的消息-r03";

        channel.basicPublish(Exchange_Name, Routing_Key_01, null, message01.getBytes("UTF-8"));
        channel.basicPublish(Exchange_Name, Routing_Key_02, null, message02.getBytes("UTF-8"));
        channel.basicPublish(Exchange_Name, Routing_Key_03, null, message03.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        CommonUtil.close(channel,connection);

    }
}
