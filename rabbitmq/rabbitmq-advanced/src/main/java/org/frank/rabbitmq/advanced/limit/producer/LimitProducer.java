package org.frank.rabbitmq.advanced.limit.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LimitProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(Constant.LIMIT_EXCHANGE_NAME_01,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Constant.LIMIT_QUEUE_NAME_01,true,false,false,null);
        
        channel.queueBind(Constant.LIMIT_QUEUE_NAME_01,Constant.LIMIT_EXCHANGE_NAME_01,"");
        
        String message = "fanoutExchange-publish message01";
        
       
        for(int i=0;i<10;i++){
            channel.basicPublish(Constant.LIMIT_EXCHANGE_NAME_01,"",null,message.getBytes());
        }        

        System.out.println("生产者发送消息成功---> ");

//        CommonUtil.close(channel,connection);
        
    }
}
