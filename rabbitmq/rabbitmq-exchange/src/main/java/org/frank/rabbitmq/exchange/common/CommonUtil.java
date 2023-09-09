package org.frank.rabbitmq.exchange.common;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CommonUtil {

    public static Connection createConnection(String host, String username, 
                                              String password, int port, 
                                              String virtualHost) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        
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
