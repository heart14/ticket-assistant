package com.heart.ticket.service.mt;

import com.meituan.sdk.model.tuangouself.coupon.couponConsume.CouponConsumeResponse;
import com.meituan.sdk.model.tuangouself.coupon.couponQuery.CouponResponse;

import java.util.List;

/**
 * @author wfli
 * @since 2024/3/13 16:00
 */
public interface MtSdkTuangouSelfService {

    List<CouponResponse> couponQuery();

    CouponConsumeResponse couponConsume(List<String> codes,String idempotent);
}
