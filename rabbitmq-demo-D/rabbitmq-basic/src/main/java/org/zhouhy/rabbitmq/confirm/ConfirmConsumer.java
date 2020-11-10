package org.zhouhy.rabbitmq.confirm;

import com.rabbitmq.client.*;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RabbitMQConstants.Confirm_Exchange_Name,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(RabbitMQConstants.Confirm_Queue_Name_01,true,false,false,null);
        channel.queueBind(RabbitMQConstants.Confirm_Queue_Name_01,RabbitMQConstants.Confirm_Exchange_Name,RabbitMQConstants.Confirm_Routing_Key_01);

        Consumer consumer = CommUtil.createConsumer(channel);

        channel.basicConsume(RabbitMQConstants.Confirm_Queue_Name_01, true, consumer);
        
        
    }
}
