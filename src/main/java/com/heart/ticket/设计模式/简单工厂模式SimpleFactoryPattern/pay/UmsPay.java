package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pay;

/**
 * @author wfli
 * @since 2024/1/29 15:42
 */
public class UmsPay implements Pay {

    @Override
    public void createOrder() {
        System.out.println("create ums order");
    }

    @Override
    public void payOrder() {
        System.out.println("pay ums order");
    }

    @Override
    public void queryOrder() {
        System.out.println("query ums order");
    }
}
