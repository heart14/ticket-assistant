package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pay;

/**
 * @author wfli
 * @since 2024/1/29 15:34
 */
public class AliPay implements Pay{
    @Override
    public void createOrder() {
        System.out.println("create alipay order");
    }

    @Override
    public void payOrder() {
        System.out.println("pay alipay order");
    }

    @Override
    public void queryOrder() {
        System.out.println("query alipay order");
    }
}
