package com.heart.ticket.service;

import com.heart.ticket.base.enums.RespCode;
import com.heart.ticket.base.exceptions.SysException;
import com.meituan.sdk.DefaultMeituanClient;
import com.meituan.sdk.MeituanClient;
import com.meituan.sdk.MeituanResponse;
import com.meituan.sdk.internal.exceptions.MtSdkException;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryCatList.DishCategoryInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryCatList.DishQueryCatListRequest;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryPropertyList.DishPropertyInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryPropertyList.DishQueryPropertyListRequest;
import com.meituan.sdk.model.waimaiNg.dish.foodQueryList.FoodInfo;
import com.meituan.sdk.model.waimaiNg.dish.foodQueryList.FoodQueryListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * About:
 * Other:
 * Created: wfli on 2023/6/19 15:28.
 * Editored:
 */
@Service
public class MtSdkDemoService {

    private static final Logger log = LoggerFactory.getLogger(MtSdkDemoService.class);

    public List<DishCategoryInfo> queryCatList(long developerId, String signKey, String appAuthToken) {
        //构造meituanClient，推荐使用单例方式，一个developer只使用一个实例
        MeituanClient meituanClient = DefaultMeituanClient.builder(developerId, signKey).build();
        //准备请求参数
        DishQueryCatListRequest dishQueryCatListRequest = new DishQueryCatListRequest();
        try {
            //发起接口调用
            MeituanResponse<List<DishCategoryInfo>> response = meituanClient.invokeApi(dishQueryCatListRequest, appAuthToken);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(Integer.valueOf(response.getCode()), response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.MTSDK_EXCEPTION.getCode(), RespCode.MTSDK_EXCEPTION.getMsg());
        }
    }

    public List<FoodInfo> queryFoodList(long developerId, String signKey, String appAuthToken) {
        //构造meituanClient，推荐使用单例方式，一个developer只使用一个实例
        MeituanClient meituanClient = DefaultMeituanClient.builder(developerId, signKey).build();
        //准备请求参数
        FoodQueryListRequest foodQueryListRequest = new FoodQueryListRequest();
        foodQueryListRequest.setOffset(0);
        foodQueryListRequest.setLimit(10);
        foodQueryListRequest.setOrderEntranceType(0);
        try {
            //发起接口调用
            MeituanResponse<List<FoodInfo>> response = meituanClient.invokeApi(foodQueryListRequest, appAuthToken);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(Integer.valueOf(response.getCode()), response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.MTSDK_EXCEPTION.getCode(), RespCode.MTSDK_EXCEPTION.getMsg());
        }
    }

    public List<DishPropertyInfo> queryPropertyList(long developerId, String signKey, String appAuthToken, String eDishCode) {
        //构造meituanClient，推荐使用单例方式，一个developer只使用一个实例
        MeituanClient meituanClient = DefaultMeituanClient.builder(developerId, signKey).build();
        //准备请求参数
        DishQueryPropertyListRequest propertyListRequest = new DishQueryPropertyListRequest();
        propertyListRequest.setEDishCode(eDishCode);
        try {
            //发起接口调用
            MeituanResponse<List<DishPropertyInfo>> response = meituanClient.invokeApi(propertyListRequest, appAuthToken);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(Integer.valueOf(response.getCode()), response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.MTSDK_EXCEPTION.getCode(), RespCode.MTSDK_EXCEPTION.getMsg());
        }
    }
}
