package org.zhouhy.rabbitmq.message;

import com.rabbitmq.client.*;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ConsumerWithProperties {

    private static final String EXCHANGE_NAME="rabbit:mq05:exchange:e01";
    private static final String QUEUE_NAME_01="rabbit:mq05:queue:q01";
    private static final String QUEUE_NAME_02="rabbit:mq05:queue:q01";

    private static final String ROUTING_KEY_01="rabbit:mq05:routing:key:r01";
    private static final String ROUTING_KEY_02="rabbit:mq05:routing:key:r02";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME_02,true, false, false, null);
        channel.queueBind(QUEUE_NAME_02,EXCHANGE_NAME,ROUTING_KEY_01);

//        channel.basicConsume(QUEUE_NAME_02, false, "myConsumerTag",
//                new DefaultConsumer(channel) {
//                    @Override
//                    public void handleDelivery(String consumerTag,
//                                               Envelope envelope,
//                                               AMQP.BasicProperties properties,
//                                               byte[] body)
//                            throws IOException{
//                        String routingKey = envelope.getRoutingKey();
//                        Map<String,Object> headers = properties.getHeaders();
//                        long deliveryTag = envelope.getDeliveryTag();
//
//                        channel.basicAck(deliveryTag, false);
//                    }
//                });

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者接收到消息成功---> "+message);
                if(properties!=null){
                    Map<String,Object> headers = properties.getHeaders();
                    for(Map.Entry<String,Object> entry:headers.entrySet()){
                        System.out.println(entry.getKey()+":"+entry.getValue());
                    }
                }
            }
        };

        channel.basicConsume(QUEUE_NAME_02, true, consumer);

    }
}
