package com.heart.ticket.service.wxpay.service.impl;

import com.heart.ticket.service.wxpay.service.WxPayService;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    private final NativePayService nativePayService;

    public WxPayServiceImpl(NativePayService nativePayService) {
        this.nativePayService = nativePayService;
    }

    @Override
    public String V3PayTransactionsNative() {
        PrepayRequest prepayRequest = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(100);
        prepayRequest.setAmount(amount);
        prepayRequest.setAppid("wxa9d9651ae******");
        prepayRequest.setMchid("190000****");
        prepayRequest.setDescription("测试商品标题");
        prepayRequest.setNotifyUrl("https://notify_url");
        prepayRequest.setOutTradeNo("out_trade_no_001");
        // 调用下单方法，得到应答
        PrepayResponse response = nativePayService.prepay(prepayRequest);
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        log.info("native create order response: {}", response);
        return response.getCodeUrl();
    }
}
