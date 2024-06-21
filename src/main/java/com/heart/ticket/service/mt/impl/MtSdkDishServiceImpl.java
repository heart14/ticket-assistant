package com.heart.ticket.service.mt.impl;

import com.alibaba.fastjson.JSON;
import com.heart.ticket.base.enums.RespCode;
import com.heart.ticket.base.exceptions.SysException;
import com.heart.ticket.service.mt.MtSdkBaseService;
import com.heart.ticket.service.mt.MtSdkDishService;
import com.meituan.sdk.MeituanResponse;
import com.meituan.sdk.internal.exceptions.MtSdkException;
import com.meituan.sdk.model.waimaiNg.dish.dishBatchUpload.DishBatchUploadRequest;
import com.meituan.sdk.model.waimaiNg.dish.dishFoodListAll.DishFoodListAllRequest;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryCatList.DishCategoryInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryCatList.DishQueryCatListRequest;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryListByEpoiid.DishInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryPropertyList.DishPropertyInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryPropertyList.DishQueryPropertyListRequest;
import com.meituan.sdk.model.waimaiNg.dish.dishSkuSellStatus.DishSkuSellStatusRequest;
import com.meituan.sdk.model.waimaiNg.dish.dishSkuSellStatus.FoodData;
import com.meituan.sdk.model.waimaiNg.dish.dishUpdatePrice.DishUpdatePriceRequest;
import com.meituan.sdk.model.waimaiNg.dish.foodBatchGet.FoodBatchGetRequest;
import com.meituan.sdk.model.waimaiNg.dish.foodBatchGet.FoodBatchGetResponse;
import com.meituan.sdk.model.waimaiNg.dish.foodQueryList.FoodInfo;
import com.meituan.sdk.model.waimaiNg.dish.foodQueryList.FoodQueryListRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * About:
 * Other:
 * Created: wfli on 2023/7/5 14:30.
 * Editored:
 */
@Service
@Slf4j
public class MtSdkDishServiceImpl extends MtSdkBaseService implements MtSdkDishService {

    @Override
    public List<DishCategoryInfo> queryCatList() {
        //准备请求参数
        DishQueryCatListRequest dishQueryCatListRequest = new DishQueryCatListRequest();
        try {
            //发起接口调用
            MeituanResponse<List<DishCategoryInfo>> response = meituanClient.invokeApi(dishQueryCatListRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public List<FoodInfo> queryFoodList() {
        //准备请求参数
        FoodQueryListRequest foodQueryListRequest = new FoodQueryListRequest();
        foodQueryListRequest.setOffset(0);
        foodQueryListRequest.setLimit(10);
        foodQueryListRequest.setOrderEntranceType(0);
        try {
            //发起接口调用
            MeituanResponse<List<FoodInfo>> response = meituanClient.invokeApi(foodQueryListRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public List<DishPropertyInfo> queryPropertyList(String eDishCode) {
        //准备请求参数
        DishQueryPropertyListRequest propertyListRequest = new DishQueryPropertyListRequest();
        propertyListRequest.setEDishCode(eDishCode);
        try {
            //发起接口调用
            MeituanResponse<List<DishPropertyInfo>> response = meituanClient.invokeApi(propertyListRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public String dishBatchUpload(String ePoiId, List<DishInfo> dishes) {
        DishBatchUploadRequest dishBatchUploadRequest = new DishBatchUploadRequest();
        dishBatchUploadRequest.setEPoiId(ePoiId);
        dishBatchUploadRequest.setDishes(JSON.toJSONString(dishes));
        dishBatchUploadRequest.setSkuOverwrite(Boolean.FALSE);
        try {
            //发起接口调用
            MeituanResponse<String> response = meituanClient.invokeApi(dishBatchUploadRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public String dishUpdatePrice(String ePoiId, String dishSkuPrices) {
        DishUpdatePriceRequest dishUpdatePriceRequest = new DishUpdatePriceRequest();
        dishUpdatePriceRequest.setEPoiId(ePoiId);
        dishUpdatePriceRequest.setDishSkuPrices(dishSkuPrices);
        try {
            //发起接口调用
            MeituanResponse<String> response = meituanClient.invokeApi(dishUpdatePriceRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public String dishSkuSellStatus(Integer sellStatus, List<FoodData> foodData) {
        DishSkuSellStatusRequest dishSkuSellStatusRequest = new DishSkuSellStatusRequest();
        dishSkuSellStatusRequest.setSellStatus(sellStatus);
        dishSkuSellStatusRequest.setFoodData(foodData);
        try {
            //发起接口调用
            MeituanResponse<String> response = meituanClient.invokeApi(dishSkuSellStatusRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public List<com.meituan.sdk.model.waimaiNg.dish.dishFoodListAll.FoodInfo> dishFoodListAll(int offset, int limit) {
        DishFoodListAllRequest dishFoodListAllRequest = new DishFoodListAllRequest();
        dishFoodListAllRequest.setOffset(offset);
        dishFoodListAllRequest.setLimit(limit);
        try {
            //发起接口调用
            MeituanResponse<List<com.meituan.sdk.model.waimaiNg.dish.dishFoodListAll.FoodInfo>> response = meituanClient.invokeApi(dishFoodListAllRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }

    @Override
    public FoodBatchGetResponse foodBatchGet(String appFoodCodes) {
        FoodBatchGetRequest foodBatchGetRequest = new FoodBatchGetRequest();
        foodBatchGetRequest.setAppFoodCodes(appFoodCodes);
        try {
            //发起接口调用
            MeituanResponse<FoodBatchGetResponse> response = meituanClient.invokeApi(foodBatchGetRequest, APP_AUTH_TOKEN);
            log.info("mt sdk response: {}", response);
            //判断是调用是否成功
            if (response.isSuccess()) {
                //调用成功，通过getData获取接口响应数据
                return response.getData();
            } else {
                //调用失败，通过getCode和getMsg获取错误码和错误描述
                throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), "[" + response.getCode() + "]" + response.getMsg());
            }
        } catch (MtSdkException e) {
            throw new SysException(RespCode.BIZ_MTSDK_EXCEPTION.getCode(), RespCode.BIZ_MTSDK_EXCEPTION.getMsg());
        }
    }
}
