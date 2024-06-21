package com.heart.ticket.service.mt.impl;

import com.heart.ticket.base.enums.RespCode;
import com.heart.ticket.base.exceptions.SysException;
import com.heart.ticket.service.mt.MtSdkBaseService;
import com.heart.ticket.service.mt.MtSdkTuangouSelfService;
import com.meituan.sdk.MeituanResponse;
import com.meituan.sdk.internal.exceptions.MtSdkException;
import com.meituan.sdk.model.tuangouself.coupon.couponConsume.CouponConsumeRequest;
import com.meituan.sdk.model.tuangouself.coupon.couponConsume.CouponConsumeResponse;
import com.meituan.sdk.model.tuangouself.coupon.couponQuery.CouponQueryRequest;
import com.meituan.sdk.model.tuangouself.coupon.couponQuery.CouponResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wfli
 * @since 2024/3/13 16:00
 */
@Service
@Slf4j
public class MtSdkTuangouSelfServiceImpl extends MtSdkBaseService implements MtSdkTuangouSelfService {

    @Override
    public List<CouponResponse> couponQuery() {

        CouponQueryRequest couponQueryRequest = new CouponQueryRequest();
        try {
            //发起接口调用
            MeituanResponse<List<CouponResponse>> response = meituanClient.invokeApi(couponQueryRequest, APP_AUTH_TOKEN);
            log.info("mt sdk couponQuery response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(Integer.valueOf(response.getCode()), response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public CouponConsumeResponse couponConsume(List<String> codes, String idempotent) {
        CouponConsumeRequest couponConsumeRequest = new CouponConsumeRequest();
        couponConsumeRequest.setCodes(codes);
        couponConsumeRequest.setIdempotent(idempotent);
        try {
            //发起接口调用
            MeituanResponse<CouponConsumeResponse> response = meituanClient.invokeApi(couponConsumeRequest, APP_AUTH_TOKEN);
            log.info("mt sdk couponQuery response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(Integer.valueOf(response.getCode()), response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }
}
