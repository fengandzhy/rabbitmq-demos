package org.frank.rabbitmq.exchange.fanout.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;
import org.frank.rabbitmq.exchange.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutConsumer1 {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();
        
        //绑定交换机，fanout扇形，即广播
        channel.exchangeDeclare(Constant.FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //获取队列
        String queueName = channel.queueDeclare().getQueue();

        System.out.println(queueName);

        //绑定交换机和队列, fanout交换机不用routing key
        channel.queueBind(queueName,Constant.FANOUT_EXCHANGE_NAME,"");

        channel.basicConsume(queueName,false,CommonUtil.createConsumer(channel));
    }
}
