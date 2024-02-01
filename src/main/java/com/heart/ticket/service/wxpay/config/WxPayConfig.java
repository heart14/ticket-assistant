package com.heart.ticket.service.wxpay.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wfli
 * @since 2024/2/1 16:54
 */
@Data
@Slf4j
@Configuration
public class WxPayConfig {

    /**
     * 商户号
     */
    public static String merchantId = "190000****";
    /**
     * 商户API私钥路径
     */
    public static String privateKeyPath = "/Users/yourname/your/path/apiclient_key.pem";
    /**
     * 商户证书序列号
     */
    public static String merchantSerialNumber = "5157F09EFDC096DE15EBE81A47057A72********";
    /**
     * 商户APIV3密钥
     */
    public static String apiV3Key = "...";

    @Bean
    public NativePayService getWxPayService() {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        // 构建service
        return new NativePayService.Builder().config(config).build();
    }
}
