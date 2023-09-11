package org.frank.rabbitmq.exchange.direct.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;
import org.frank.rabbitmq.exchange.common.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DirectProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(Constant.DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        
        String error = "Hello, I am an error message.";
        String info = "Hello, I am an info message.";
        String debug = "Hello, I am a debug message.";
        
        
        channel.basicPublish(Constant.DIRECT_EXCHANGE_NAME,Constant.ERROR_ROUTING_KEY,null, error.getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(Constant.DIRECT_EXCHANGE_NAME,Constant.INFO_ROUTING_KEY,null, info.getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(Constant.DIRECT_EXCHANGE_NAME,Constant.DEBUG_ROUTING_KEY,null, debug.getBytes(StandardCharsets.UTF_8));
        
        System.out.println("广播消息发送成功");
    }
}
