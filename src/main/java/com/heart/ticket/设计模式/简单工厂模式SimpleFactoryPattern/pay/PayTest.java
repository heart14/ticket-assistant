package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pay;

/**
 * @author wfli
 * @since 2024/1/29 15:37
 */
public class PayTest {

    public static void main(String[] args) {

        // ....
        // 前置步骤，进行到支付过程

        Pay pay = PayFactory.getInstance("ums");
        pay.createOrder();
        pay.payOrder();
        pay.queryOrder();

        System.out.println("支付完成");

        // 如果需要新增一个支付渠道
        // 1.实现Pay接口 进行新渠道的自定义实现
        // 2.在Factory工厂类里添加新渠道类的创建方法
    }
}
