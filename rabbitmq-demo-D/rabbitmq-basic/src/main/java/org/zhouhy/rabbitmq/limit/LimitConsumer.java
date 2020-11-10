package org.zhouhy.rabbitmq.limit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.MyCommonComsumer;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LimitConsumer {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

        
        Consumer consumer = new MyCommonComsumer(channel);

        
        /**
         * 如果要限流这里的autoAck一定要设置成false
         * prefetchSize: 消息大小限制
         * prefetchCount: 一次处理几条
         * 
         * */
        channel.basicQos(0,1,false);
        channel.basicConsume(RabbitMQConstants.LIMIT_QUEUE_NAME_01, false, consumer);
        
    }
}
