package com.heart.ticket.controller;

import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.service.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "微信接口测试")
@Slf4j
@RestController("/wx")
public class WxPayDemo {

    private final WxPayService wxPayService;

    public WxPayDemo(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    @GetMapping("/createOrder")
    @ApiOperation("微信创建订单")
    public SysResponse createOrder() {
        String aNative = wxPayService.V3PayTransactionsNative();
        Map<String, String> map = new HashMap<>();
        map.put("codeUrl", aNative);
        return SysResponse.success(map);
    }
}
