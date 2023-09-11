package org.frank.rabbitmq.advanced.common;

public class Constant {
    public static final String CONFIRM_EXCHANGE_NAME="rabbit:confirm:exchange:e01";
    public static final String CONFIRM_QUEUE_NAME_01="rabbit:confirm:queue:q01";
    public static final String CONFIRM_ROUTING_KEY_01="rabbit:confirm:routing:key.#";

    public static final String RETURN_EXCHANGE_NAME_01="mq:return:exchange:d01";
    public static final String RETURN_QUEUE_NAME_01="mq:return:queue:q01";
    public static final String RETURN_ROUTINE_KEY_01="mq:return:routine:k.*";
    public static final String RETURN_ROUTINE_KEY_02="mq:return:error";
}
