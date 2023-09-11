package org.frank.rabbitmq.exchange.fanout.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.frank.rabbitmq.exchange.common.CommonUtil;
import org.frank.rabbitmq.exchange.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutConsumer2 {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();

        //绑定交换机，fanout扇形，即广播
        channel.exchangeDeclare(Constant.FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Constant.FANOUT_QUEUE_NAME2, false, false, false, null);

        //绑定交换机和队列, fanout交换机不用routing key
        channel.queueBind(Constant.FANOUT_QUEUE_NAME2,Constant.FANOUT_EXCHANGE_NAME,"");

        channel.basicConsume(Constant.FANOUT_QUEUE_NAME2,false,CommonUtil.createConsumer(channel));
    }
}
