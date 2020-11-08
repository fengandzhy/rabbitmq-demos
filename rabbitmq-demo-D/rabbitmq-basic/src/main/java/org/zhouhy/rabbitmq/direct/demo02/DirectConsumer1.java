package org.zhouhy.rabbitmq.direct.demo02;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectConsumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();
        
        /**
         * 声明这个DIRECT_EXCHANGE_NAME_01 是个 Direct Exchange 
         * */
        channel.exchangeDeclare(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,BuiltinExchangeType.DIRECT);
        
        // 声明Queue
        channel.queueDeclare(RabbitMQConstants.DIRECT_QUEUE_NAME_01,true, false,false,null);
        
        /**
         *  将DIRECT_EXCHANGE_NAME_01 和 DIRECT_QUEUE_NAME_01 绑定起来, 绑定的routine key 是DIRECT_ROUTINE_KEY_01
         *  也就是说 Producer 发送到 DIRECT_EXCHANGE_NAME_01 的并且 routine key 是DIRECT_ROUTINE_KEY_01 
         *  的消息全部存储到 DIRECT_QUEUE_NAME_01 
         *  也即下面的这个message01 会被存储到 DIRECT_QUEUE_NAME_01
         *  channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,
         *                 RabbitMQConstants.DIRECT_ROUTINE_KEY_01,null,message01.getBytes());
         * */
        channel.queueBind(RabbitMQConstants.DIRECT_QUEUE_NAME_01,
                RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,RabbitMQConstants.DIRECT_ROUTINE_KEY_01);

        
//        channel.queueBind(RabbitMQConstants.DIRECT_QUEUE_NAME_01,
//                RabbitMQConstants.DIRECT_EXCHANGE_NAME_02,RabbitMQConstants.DIRECT_ROUTINE_KEY_01);

        Consumer consumer = CommUtil.createConsumer(channel);
        
        
        /**
         * 只会打印出DIRECT_QUEUE_NAME_01 里面的全部消息
         * */
        channel.basicConsume(RabbitMQConstants.DIRECT_QUEUE_NAME_01, true, consumer);
        
    }
}
