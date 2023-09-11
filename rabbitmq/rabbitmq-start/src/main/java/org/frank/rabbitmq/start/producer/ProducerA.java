package org.frank.rabbitmq.start.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.common.CommonUtil;
import org.frank.rabbitmq.common.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ProducerA {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
                
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        /**
         * 队列名称
         * 持久化配置：mq重启后还在
         * 是否独占：只能有一个消费者监听队列；当connection关闭是否删除队列，一般是false，发布订阅是独占
         * 自动删除: 当没有消费者的时候，自动删除掉，一般是false
         * 其他参数
         *
         * 队列不存在则会自动创建，如果存在则不会覆盖，所以此时的时候需要注意属性
         */
        channel.queueDeclare(Constant.START_QUEUE_NAME, false, false, false, null);
        String message = "Hello world";
        channel.basicPublish("", Constant.START_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
        
        CommonUtil.close(channel, connection);
    }
}
