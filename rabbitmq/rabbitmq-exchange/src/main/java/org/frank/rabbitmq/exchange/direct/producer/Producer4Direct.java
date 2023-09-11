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
 * 任何发送到Direct Exchange的消息都会被转发到RouteKey中指定的Queue。
 * 1.一般情况可以使用rabbitMQ自带的Exchange：”"(该Exchange的名字为空字符串，下文称其为default Exchange)。
 * 2.这种模式下不需要将Exchange进行任何绑定(binding)操作
 * 3.消息传递时需要一个“RouteKey”，可以简单的理解为要发送到的队列名字。
 * 4.如果vhost中不存在RouteKey中指定的队列名，则该消息会被抛弃。
 * 
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
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_Name,BuiltinExchangeType.DIRECT);        
        channel.queueDeclare(Queue_Name_01,false, false, false, null);
        channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);

        channel.queueDeclare(Queue_Name_02, false, false, false, null);
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
