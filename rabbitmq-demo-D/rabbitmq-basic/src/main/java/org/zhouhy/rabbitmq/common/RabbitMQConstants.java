package org.zhouhy.rabbitmq.common;

public class RabbitMQConstants {
    public static final String Confirm_Exchange_Name="rabbit:confirm:exchange:e01";
    public static final String Confirm_Queue_Name_01="rabbit:confirm:queue:q01";
    public static final String Confirm_Routing_Key_01="rabbit:confirm:routing:key:#";
    
    
    public static final String DIRECT_EXCHANGE_NAME_01="mq:direct:exchange:d01";
    public static final String DIRECT_EXCHANGE_NAME_02="mq:direct:exchange:d02";
    public static final String DIRECT_QUEUE_NAME_01="mq:direct:queue:q01";
    public static final String DIRECT_QUEUE_NAME_02="mq:direct:queue:q02";
    public static final String DIRECT_ROUTINE_KEY_01="mq:direct:routine:k01";
    public static final String DIRECT_ROUTINE_KEY_02="mq:direct:routine:k02";


    public static final String FANOUT_EXCHANGE_NAME_01="mq:fanout:exchange:d01";
    public static final String FANOUT_EXCHANGE_NAME_02="mq:fanout:exchange:d02";
    public static final String FANOUT_QUEUE_NAME_01="mq:fanout:queue:q01";
    public static final String FANOUT_QUEUE_NAME_02="mq:fanout:queue:q02";
}
