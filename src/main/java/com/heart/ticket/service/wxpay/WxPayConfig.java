package com.heart.ticket.service.wxpay;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author wfli
 * @since 2024/2/1 16:54
 */
@Data
@Slf4j
@Configuration
public class WxPayConfig {

    private String mchId;
    private String mchSerial;
    private String privateKeyPath;
}
