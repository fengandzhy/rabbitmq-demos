package org.frank.rabbitmq.advanced.limit.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class LimitConsumer {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection();
        Channel channel = connection.createChannel();

        
        Consumer consumer = new MyCommonConsumer(channel);

        
        /**
         * 如果要限流这里的autoAck一定要设置成false
         * prefetchSize: 消息大小限制 0 表示无上限
         * prefetchCount: 一次处理几条
         * global 是否是全局的
         * */
        channel.basicQos(0,1,false);
        
        /**
         * 注意这里的第二个参数一定一定要是false, 要在我们新写的consumer里面手动确认签收
         * */
        channel.basicConsume(Constant.LIMIT_QUEUE_NAME_01, false, consumer);
        
    }
}
