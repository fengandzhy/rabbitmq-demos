package org.frank.rabbitmq.advanced.retu.listener.producer;

import com.rabbitmq.client.*;
import org.frank.rabbitmq.advanced.common.CommonUtil;
import org.frank.rabbitmq.advanced.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReturnSender {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = CommonUtil.createConnection("47.242.251.45",
                "admin", "123456",
                5672, "/");
        Channel channel = connection.createChannel();

        channel.confirmSelect();

        String message01 = "directExchange-publish message-r01";

        //如果消息没有被路由到某个队列中就会调用这个方法
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                    String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("The message is coming back "+ new String(body));
            }
        });

        //这里的mandatory一定要设置成true 否则这个return listener 不起作用
        channel.basicPublish(Constant.RETURN_EXCHANGE_NAME_01,
                Constant.RETURN_ROUTINE_KEY_02, true, null, 
                message01.getBytes("UTF-8"));

        channel.addConfirmListener(new ConfirmListener() {

            /**
             * 如果成功投递到broker 它会给我们调用这个方法
             * */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------ack!-----------");
            }

            /**
             * 如果没有成功投递到broker 它会给我们调用这个方法
             * */
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------no ack!-----------");
            }
        });
        
    }
}
