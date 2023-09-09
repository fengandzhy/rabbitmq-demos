package org.frank.rabbitmq.round.robin.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.common.CommonUtil;
import org.frank.rabbitmq.common.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 轮询就是生产者依次向不同的消费者发送消息, 就是说生产者发送了一堆消息, 每个消费者依次来读取, 
 * 缺点就是有些消费者消费消息快, 而有些消费者消费消息慢,
 * channel.basicQos(1); 这个代码就可以让能者多劳了
 * */
public class ProducerForRoundRobin {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();
        channel.queueDeclare(Constant.ROUND_ROBIN_QUEUE_NAME, false, false, false, null);
        for(int i = 0; i < 10; i++){
            String message = "You are the message "+ i;
            channel.basicPublish("", Constant.ROUND_ROBIN_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
        CommonUtil.close(channel, connection);
    }
}
