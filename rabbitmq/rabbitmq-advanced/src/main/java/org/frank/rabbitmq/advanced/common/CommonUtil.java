package org.frank.rabbitmq.advanced.common;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CommonUtil {

    public static Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.242.251.45");
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        return factory.newConnection();
    }

    public static void close(Channel channel, Connection connection) throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public static Consumer createConsumer(Channel channel){

        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者接收到消息成功---> "+message);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
    }
}
