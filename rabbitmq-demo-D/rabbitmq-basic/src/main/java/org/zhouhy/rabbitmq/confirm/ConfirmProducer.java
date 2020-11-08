package org.zhouhy.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        //消息确认开启
        channel.confirmSelect();

        String message = "Hello rabbit MQ confirm message!";

        //发送消息永远这两个最重要一个exchange name 一个是routing key
        channel.basicPublish(RabbitMQConstants.Confirm_Exchange_Name,
                RabbitMQConstants.Confirm_Routing_Key_01, null,
                message.getBytes("UTF-8"));

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------no ack!-----------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------ack!-----------");
            }
        });


    }
}
