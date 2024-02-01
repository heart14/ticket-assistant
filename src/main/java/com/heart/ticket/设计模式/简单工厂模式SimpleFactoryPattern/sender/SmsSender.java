package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.sender;

/**
 * @author wfli
 * @since 2024/1/29 15:04
 */
public class SmsSender implements Sender {
    @Override
    public void send() {
        System.out.println("sms sender!");
    }
}
