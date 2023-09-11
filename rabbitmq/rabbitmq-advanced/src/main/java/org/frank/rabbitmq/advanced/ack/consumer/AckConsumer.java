package org.frank.rabbitmq.advanced.ack.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AckConsumer {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Constant.ACK_EXCHANGE_NAME_01,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(Constant.ACK_QUEUE_NAME_01,true,false,false,null);

        channel.queueBind(Constant.ACK_QUEUE_NAME_01,
                Constant.ACK_EXCHANGE_NAME_01,Constant.ACK_ROUTINE_KEY_01);

        Consumer consumer = new MyAckConsumer(channel);       
        
        /**
         * 这个地方不能加这条语句否则消息就不会重新回到队尾
         * */
        //channel.basicQos(0,1,false);
        channel.basicConsume(Constant.ACK_QUEUE_NAME_01, false, consumer);        
    }
}
