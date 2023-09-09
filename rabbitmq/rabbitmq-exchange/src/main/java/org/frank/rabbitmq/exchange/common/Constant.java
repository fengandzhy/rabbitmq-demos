package org.frank.rabbitmq.exchange.common;

public class Constant {
    public static final String FANOUT_EXCHANGE_NAME="fanout_exchange_name";
    public static final String FANOUT_QUEUE_NAME1="fanout_queue_name1";
    public static final String FANOUT_QUEUE_NAME2="fanout_queue_name2";

    public static final String DIRECT_EXCHANGE_NAME="direct_exchange_name";
    public static final String DIRECT_QUEUE_NAME1="direct_queue_name1";
    public static final String DIRECT_QUEUE_NAME2="direct_queue_name2";
    public static final String ERROR_ROUTING_KEY = "error_routing_key";
    public static final String INFO_ROUTING_KEY = "info_routing_key";
    public static final String DEBUG_ROUTING_KEY = "debug_routing_key";

    public static final String TOPIC_EXCHANGE_NAME_01="mq:topic:exchange:d01";
    public static final String TOPIC_QUEUE_NAME_01="mq:topic:queue:q01";
    public static final String TOPIC_ROUTINE_KEY_01="mq:topic:routine:k.*";
}
