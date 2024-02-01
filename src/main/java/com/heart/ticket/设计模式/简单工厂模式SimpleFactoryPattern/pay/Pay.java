package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pay;

/**
 * @author wfli
 * @since 2024/1/29 15:31
 */
public interface Pay {

    /**
     * 抽象支付接口，分别定义创建支付订单，进行支付，查询支付结果三个接口
     */
    public void createOrder();
    public void payOrder();
    public void queryOrder();
}
