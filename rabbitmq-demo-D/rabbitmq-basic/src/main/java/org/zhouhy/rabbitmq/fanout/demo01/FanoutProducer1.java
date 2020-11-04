package org.zhouhy.rabbitmq.fanout.demo01;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutProducer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(RabbitMQConstants.FANOUT_QUEUE_NAME_01,true,false,false,null);
        channel.queueDeclare(RabbitMQConstants.FANOUT_QUEUE_NAME_02,true,false,false,null);
        
        channel.queueBind(RabbitMQConstants.FANOUT_QUEUE_NAME_01,RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,"");
        channel.queueBind(RabbitMQConstants.FANOUT_QUEUE_NAME_02,RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,"");

        String message = "fanoutExchange-publish message01";
        
        /**
         * 就是当Producer 往这个exchange 里面发送消息时, 所有绑定到这个 exchange 上的queue 都会接受到消息
         * 也就是说FANOUT_QUEUE_NAME_01 FANOUT_QUEUE_NAME_02 将收到同样的消息
         * */
        channel.basicPublish(RabbitMQConstants.FANOUT_EXCHANGE_NAME_01,"",null,message.getBytes());

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);
        
    }
}
