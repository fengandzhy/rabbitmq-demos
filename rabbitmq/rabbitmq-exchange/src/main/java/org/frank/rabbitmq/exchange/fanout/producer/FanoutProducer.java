package org.frank.rabbitmq.exchange.fanout.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.fanout.common.CommonUtil;
import org.frank.rabbitmq.exchange.fanout.common.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class FanoutProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(Constant.FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        
        String msg = "Hello I am a fanout message.";
        channel.basicPublish(Constant.FANOUT_EXCHANGE_NAME,"",null,msg.getBytes(StandardCharsets.UTF_8));

        System.out.println("广播消息发送成功");
    }
}
