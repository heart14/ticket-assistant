package com.heart.ticket.service.geek;

/**
 * @author wfli
 * @since 2024/5/8 17:58
 */
public interface GeekTimeService {

    void login(String cellphone,String password);

    void geekTime();

    void geekTimeCyclic();
}
