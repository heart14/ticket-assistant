package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.sender;

/**
 * @author wfli
 * @since 2024/1/29 15:05
 */
public class SenderFactory {

    public Sender create(String type) {
        if ("mail".equals(type)) {
            return new MailSender();
        } else if ("sms".equals(type)) {
            return new SmsSender();
        } else {
            System.out.println("模式不支持");
            return null;
        }
    }
}
