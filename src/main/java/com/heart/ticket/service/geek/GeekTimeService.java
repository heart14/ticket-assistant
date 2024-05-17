package com.heart.ticket.service.geek;

/**
 * @author wfli
 * @since 2024/5/8 17:58
 */
public interface GeekTimeService {

    /**
     * 设置较大的pageSize，一把查询所有个人中心-我的课程，然后进行下载
     */
    void geekTime(String cellphone,String password);

    /**
     * 分页查询个人中心-我的课程，然后进行下载
     */
    void geekTimeCyclic();

    /**
     * 根据skuId下载单个课程
     *
     * @param skuId
     */
    void geekTime(long skuId);
}
