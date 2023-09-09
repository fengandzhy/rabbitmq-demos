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
 * 1.这种模式较为复杂，简单来说，就是每个队列都有其关心的主题，所有的消息都带有一个“标题”(RouteKey)，Exchange会将消息转发到所有关注主题能与RouteKey模糊匹配的队列。
 * 2.这种模式需要RouteKey，也许要提前绑定Exchange与Queue。
 * 3.在进行绑定时，要提供一个该队列关心的主题，如“#.log.#”表示该队列关心所有涉及log的消息(一个RouteKey为”MQ.log.error”的消息会被转发到该队列)。
 * 4.“#”表示0个或若干个关键字，“”表示一个关键字。如“log.”能与“log.warn”匹配，无法与“log.warn.timeout”匹配；但是“log.#”能与上述两者匹配。
 * 5.同样，如果Exchange没有发现能够与RouteKey匹配的Queue，则会抛弃此消息。
 * 
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
