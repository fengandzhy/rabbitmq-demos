package org.frank.rabbitmq.exchange.topic.producer;

import com.rabbitmq.client.*;
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
 * 
 * 同一条消息，发送到一个exchange, 然后有两个 routing key 例如 rabbit:mq04:routing:key:r.orange 和 rabbit:mq04:routing:key:r.orange.apple,
 * 在consumer中, rabbit:mq04:queue:q01 这个queue 就能接收到这条消息, 因为它的routing key 是 rabbit:mq04:routing:key:r.# 
 * 它有两个routing key 并不意味着它会发送到两个queue 中, routing key 的意思是满足这个条件的 queue 会接受到消息而已. 
 * */
public class Producer4Topic {

    private static final String Exchange_Name="rabbit:mq04:exchange:e01";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.TOPIC);
        String message = "topicExchange-publish我的消息";

        channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange", true,null, message.getBytes("UTF-8"));
        channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange.apple", null, message.getBytes("UTF-8"));

        System.out.println("生产者发送消息成功---> ");

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("The message is coming back "+ new String(body));
            }
        });

//        CommonUtil.close(channel,connection);

    }
}
