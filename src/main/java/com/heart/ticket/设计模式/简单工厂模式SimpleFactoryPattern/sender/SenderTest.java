package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.sender;

/**
 * @author wfli
 * @since 2024/1/29 15:07
 */
public class SenderTest {

    /**
     * 所以在支付项目中，微信支付和支付宝支付就可以进行抽象出来
     * @param args
     */
    public static void main(String[] args) {
        SenderFactory senderFactory = new SenderFactory();
        Sender sender1 = senderFactory.create("sms");
        sender1.send();
        Sender sender2 = senderFactory.create("mail");
        sender2.send();
    }
}
