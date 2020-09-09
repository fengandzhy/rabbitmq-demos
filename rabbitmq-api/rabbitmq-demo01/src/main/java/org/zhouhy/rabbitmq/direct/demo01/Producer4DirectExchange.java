package org.zhouhy.rabbitmq.direct.demo01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer4DirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = CommUtil.createConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";

        String msg = "Hello World RabbitMQ 4  Direct Exchange Message 111 ... ";
        channel.basicPublish(exchangeName, routingKey , null , msg.getBytes());

        CommUtil.close(channel,connection);


    }
}
