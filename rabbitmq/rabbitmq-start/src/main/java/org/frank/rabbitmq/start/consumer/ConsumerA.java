package org.frank.rabbitmq.start.consumer;

import com.rabbitmq.client.*;
import org.frank.rabbitmq.start.common.CommonUtil;
import org.frank.rabbitmq.start.common.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class ConsumerA {
    
    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Properties properties = new Properties();
        
        InputStream in = ConsumerA.class.getClassLoader().getResourceAsStream("config.properties");
        
        properties.load(in);
        
        Connection connection = CommonUtil.createConnection(properties.getProperty("host"),
                properties.getProperty("username"), properties.getProperty("password"),
                Integer.parseInt(properties.getProperty("port")), properties.getProperty("virtualHost"));
        Channel channel = connection.createChannel();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] received '" + message + "'");
        };
        channel.basicConsume(Constant.START_QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        
        CommonUtil.close(channel, connection);
    }
}
