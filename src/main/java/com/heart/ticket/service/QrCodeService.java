package com.heart.ticket.service;

import com.heart.ticket.base.utils.QrCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wfli
 * @since 2024/3/28 10:20
 */
@Service
public class QrCodeService {

    @Autowired
    private QrCodeUtils qrCodeUtils;

    public String createQrCode(String content) {
        return qrCodeUtils.getBase64QRCode(content);
    }
}
