package org.frank.rabbitmq.start.consumer;

import com.rabbitmq.client.*;
import org.frank.rabbitmq.start.common.CommonUtil;
import org.frank.rabbitmq.start.common.Constant;

import java.io.IOException;
import java.io.InputStream;
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
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] received '" + message + "'");
//        };
//        channel.basicConsume(Constant.START_QUEUE_NAME, true, deliverCallback, consumerTag -> {});

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                // consumerTag 是固定的 可以做此会话的名字， deliveryTag 每次接收消息+1
                System.out.println("consumerTag 消息标识=" + consumerTag);
                //可以获取交换机，路由健等
                System.out.println("envelope元数据=" + envelope);
                System.out.println("properties 配置信息=" + properties);
                System.out.println("body=" + new String(body, "utf-8"));
            }
        };
        channel.basicConsume(Constant.START_QUEUE_NAME, true, consumer);
        
        /**
         * 我这里执行了关闭connection的操作, 所以consumer程序不会一直等下去, 如果没有这个, consumer 就会一直等下去, 具体运行Recv
         * */
        CommonUtil.close(channel, connection);
    }
}
