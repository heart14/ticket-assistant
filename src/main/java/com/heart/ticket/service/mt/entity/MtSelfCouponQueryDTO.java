package com.heart.ticket.service.mt.entity;

import com.google.gson.annotations.SerializedName;
import com.meituan.sdk.model.tuangouself.coupon.couponQuery.DealMenu;
import com.meituan.sdk.model.tuangouself.coupon.couponQuery.UserRule;

import java.util.List;

/**
 * @author wfli
 * @since 2024/3/13 16:05
 */
public class MtSelfCouponQueryDTO {

    /**
     * 项目ID
     */
    @SerializedName("dealId")
    private Integer dealId;
    /**
     * 项目名称
     */
    @SerializedName("dealTitle")
    private String dealTitle;
    /**
     * 项目类型，0套餐，1代金券
     */
    @SerializedName("dealType")
    private Integer dealType;
    /**
     * 券码
     */
    @SerializedName("code")
    private String code;
    /**
     * 券有效期开始时间，单位秒
     */
    @SerializedName("couponStartTime")
    private Long couponStartTime;
    /**
     * 券有效期结束时间，单位秒
     */
    @SerializedName("couponEndTime")
    private Long couponEndTime;
    /**
     * 券对应的订单id
     */
    @SerializedName("orderId")
    private Long orderId;
    /**
     * 项目的市场价，用于展示
     */
    @SerializedName("buyPrice")
    private String buyPrice;
    /**
     * 下单时团购券售卖价格
     */
    @SerializedName("salePrice")
    private String salePrice;
    /**
     * 用户购买价
     */
    @SerializedName("settlePrice")
    private String settlePrice;
    /**
     * 券码购买平台（0美团、1点评）
     */
    @SerializedName("platform")
    private Integer platform;
    /**
     * 券使用规则
     */
    @SerializedName("useRule")
    private UserRule useRule;
    /**
     * deal对应的菜品信息
     */
    @SerializedName("menuInfo")
    private List<DealMenu> menuInfo;
    /**
     * 是否可用
     */
    @SerializedName("canUse")
    private Boolean canUse;
    /**
     * 不可用原因
     */
    @SerializedName("unavailableReason")
    private String unavailableReason;
    /**
     * 开店宝上单商家录入的项目名称，仅套餐券使用
     */
    @SerializedName("rawTitle")
    private String rawTitle;
}
