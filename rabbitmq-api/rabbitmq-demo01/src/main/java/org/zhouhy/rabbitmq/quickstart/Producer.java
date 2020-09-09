package org.zhouhy.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        for(int i=0;i<5;i++){
            String msg = "Hello RabbitMq!";
            channel.basicPublish("","test001",null,msg.getBytes());
        }

        CommUtil.close(channel,connection);

    }
}
