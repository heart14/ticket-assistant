package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pay;

/**
 * @author wfli
 * @since 2024/1/29 15:35
 */
public class PayFactory {

    public static Pay getInstance(String type) {
        if ("wxpay".equals(type)) {
            return new WxPay();
        } else if ("alipay".equals(type)) {
            return new AliPay();
        } else if ("ums".equals(type)) {
            return new UmsPay();
        } else {
            System.out.println("支付方式暂不支持");
            return null;
        }
    }
}
