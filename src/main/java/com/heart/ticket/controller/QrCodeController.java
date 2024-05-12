package com.heart.ticket.controller;

import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.service.QrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wfli
 * @since 2024/3/28 10:40
 */
@Api(tags = "qrcode api")
@Slf4j
@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @ApiOperation("获取base64形式二维码")
    @GetMapping("/demo")
    public SysResponse qrCode(String content) {
        return SysResponse.success(qrCodeService.createQrCode(content));
    }
}
