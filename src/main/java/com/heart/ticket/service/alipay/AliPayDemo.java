package com.heart.ticket.service.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.heart.ticket.base.utils.StringUtils;

import java.nio.charset.StandardCharsets;


/**
 * @author wfli
 * @since 2024/1/26 17:29
 */
public class AliPayDemo {

    private static final String APP_ID = "appId";
    private static final String APP_PRIVATE_KEY = "appPrivateKey";
    private static final String CHARSET = StandardCharsets.UTF_8.name();
    private static final String ALIPAY_PUBLIC_KEY = "";
    private static final String FORMAT = "json";
    private static final String SIGN_TYPE = "RSA2";

    private static final AlipayClient alipayClient;

    static {
        //实例化客户端
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
    }

    public void alipay() {
        String outtradeno = StringUtils.UuidWithSplit();

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // 私钥加密 公钥解密  身份认证？

    }
}
