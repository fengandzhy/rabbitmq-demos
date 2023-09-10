package org.frank.rabbitmq.advanced.confirm.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.advanced.confirm.common.CommonUtil;
import org.frank.rabbitmq.advanced.confirm.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 首先要开启消息确认模式 channel.confirmSelect(); 
 * 然后是创建confirmListener 
 * 
 * */
public class ConfirmProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();

        //消息确认开启, 
        channel.confirmSelect();

        String message = "Hello rabbit MQ confirm message!";

        //发送消息永远这两个最重要一个exchange name 一个是routing key
        channel.basicPublish(Constant.CONFIRM_EXCHANGE_NAME,
                Constant.CONFIRM_ROUTING_KEY_01, null,
                message.getBytes("UTF-8"));

        
        
        channel.addConfirmListener(new ConfirmListener() {
            
            /**
             * 如果成功投递到broker 它会给我们返回一个 ack 
             * */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------ack!-----------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------no ack!-----------");
            }
        });


    }
}
