package org.zhouhy.rabbitmq.message;

import com.rabbitmq.client.*;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProducerWithProperties {

    private static final String EXCHANGE_NAME="rabbit:mq05:exchange:e01";
    private static final String QUEUE_NAME_01="rabbit:mq05:queue:q01";
    private static final String QUEUE_NAME_02="rabbit:mq05:queue:q01";

    private static final String ROUTING_KEY_01="rabbit:mq05:routing:key:r01";
    private static final String ROUTING_KEY_02="rabbit:mq05:routing:key:r02";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME_01,true, false, false, null);
        channel.queueBind(QUEUE_NAME_01,EXCHANGE_NAME,ROUTING_KEY_01);

        channel.queueDeclare(QUEUE_NAME_02, true, false, false, null);
        channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, ROUTING_KEY_02);

        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", "111");
        headers.put("my2", "222");

        AMQP.BasicProperties properties
                = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000") //消息在十秒内不消费的话会消失
                .headers(headers)
                .build();

        String message01 = "directExchange-publish我的消息-r01";
        for(int i=0;i<5;i++){
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_01,properties,message01.getBytes());
        }

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);

    }
}
