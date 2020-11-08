package org.zhouhy.rabbitmq.direct.demo01;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.zhouhy.rabbitmq.common.CommUtil;
import org.zhouhy.rabbitmq.common.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Direct_1 {
    /**
     * 这里的Consumer没有指定 Queue的Binding 和 Exchange 的类型, 那么就必须要在Producer里面提供相关信息
     * 而且必须先启动Producer再启动Consumer 否则就会报错. 
     * */
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommUtil.createConnection();
        Channel channel = connection.createChannel();

//        channel.exchangeDeclare(RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,BuiltinExchangeType.DIRECT);
//        
//        channel.queueBind(RabbitMQConstants.DIRECT_QUEUE_NAME_01,RabbitMQConstants.DIRECT_EXCHANGE_NAME_01,RabbitMQConstants.DIRECT_ROUTINE_KEY_01);
        Consumer consumer = CommUtil.createConsumer(channel);
        //channel.basicConsume(RabbitMQConstants.DIRECT_QUEUE_NAME_01, true, consumer);

        channel.basicConsume(RabbitMQConstants.DIRECT_QUEUE_NAME_01, true, consumer);
    }
}
