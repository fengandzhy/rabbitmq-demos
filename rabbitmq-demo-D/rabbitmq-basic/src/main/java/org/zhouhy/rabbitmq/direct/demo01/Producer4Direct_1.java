package org.zhouhy.rabbitmq.direct.demo01;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Producer4Direct_1 {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(RabbitMQConstants.DIRECT_EXCHANGE_NAME_02,BuiltinExchangeType.DIRECT);
        
        channel.queueDeclare(RabbitMQConstants.DIRECT_QUEUE_NAME_01,
                true,false,false,null);
        channel.queueDeclare(RabbitMQConstants.DIRECT_QUEUE_NAME_02,
                true,false,false,null);
        
        channel.queueBind(RabbitMQConstants.DIRECT_QUEUE_NAME_01,
                RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,RabbitMQConstants.DIRECT_ROUTINE_KEY_01);
        channel.queueBind(RabbitMQConstants.DIRECT_QUEUE_NAME_01,
                RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,RabbitMQConstants.DIRECT_ROUTINE_KEY_02);

        String message01 = "directExchange-publish我的消息-r01";
        String message02 = "directExchange-publish我的消息-r02";
        String message03 = "directExchange-publish我的消息-r03";
        
        channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,
                RabbitMQConstants.DIRECT_ROUTINE_KEY_01,null,message01.getBytes());

        channel.basicPublish(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,
                RabbitMQConstants.DIRECT_ROUTINE_KEY_02,null,message02.getBytes());
        
        log.info("生产者发送消息成功---> ");
        CommUtil.close(channel,connection);
    }
}
