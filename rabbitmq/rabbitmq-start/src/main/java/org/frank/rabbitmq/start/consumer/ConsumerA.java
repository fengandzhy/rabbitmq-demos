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
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ConsumerA.class.getClassLoader().getResourceAsStream("config.properties");
        // 使用properties对象加载输入流
        properties.load(in);
        //获取key对应的value值
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
    }
}
