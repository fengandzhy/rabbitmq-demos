package org.zhouhy.rabbitmq.limit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LimitProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(RabbitMQConstants.LIMIT_EXCHANGE_NAME_01,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(RabbitMQConstants.LIMIT_QUEUE_NAME_01,true,false,false,null);
        
        channel.queueBind(RabbitMQConstants.LIMIT_QUEUE_NAME_01,RabbitMQConstants.LIMIT_EXCHANGE_NAME_01,"");
        
        String message = "fanoutExchange-publish message01";
        
       
        for(int i=0;i<10;i++){
            channel.basicPublish(RabbitMQConstants.LIMIT_EXCHANGE_NAME_01,"",null,message.getBytes());
        }        

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);
        
    }
}
