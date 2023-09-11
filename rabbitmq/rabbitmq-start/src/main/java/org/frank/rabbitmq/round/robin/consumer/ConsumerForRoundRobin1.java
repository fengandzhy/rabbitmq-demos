package org.frank.rabbitmq.round.robin.consumer;

import com.rabbitmq.client.*;
import org.frank.rabbitmq.common.CommonUtil;
import org.frank.rabbitmq.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsumerForRoundRobin1 {
    
    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Constant.ROUND_ROBIN_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicQos(1); // 限制每次消费1条消息, 消费完了再取
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("body=" + new String(body, "utf-8"));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //消费,关闭消息消息自动确认,重要
        channel.basicConsume(Constant.ROUND_ROBIN_QUEUE_NAME,false,consumer);
    }
}
