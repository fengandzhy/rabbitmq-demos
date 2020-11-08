package org.zhouhy.rabbitmq.direct.demo02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectProducer2 {
    
    /**
     * 这里的生产者没有指定 Queue的Binding 和 Exchange 的类型, 那么就必须要在Consumer里面提供相关信息
     * 而且必须先启动Consumer再启动Producer 否则消息就会丢失. 
     * */
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        String message01 = "directExchange-publish message-r01";
        String message02 = "directExchange-publish message-r02";
        String message03 = "directExchange-publish message-r03";
        
        /**
         *  Producer 把 message01 发送到 DIRECT_EXCHANGE_NAME_01 上并附带的routing key 是 DIRECT_ROUTINE_KEY_01
         * */
        channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,
                RabbitMQConstants.DIRECT_ROUTINE_KEY_01,null,message01.getBytes());
        
        channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE_NAME_02,
                RabbitMQConstants.DIRECT_ROUTINE_KEY_02,null,message02.getBytes());
        
        channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE_NAME_02,
                RabbitMQConstants.DIRECT_ROUTINE_KEY_01,null,message03.getBytes());

        System.out.println("生产者发送消息成功---> ");

        CommUtil.close(channel,connection);
    }
}
