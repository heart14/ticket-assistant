package com.heart.ticket.service.mt;

import com.meituan.sdk.model.waimaiNg.dish.dishQueryCatList.DishCategoryInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryListByEpoiid.DishInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryPropertyList.DishPropertyInfo;
import com.meituan.sdk.model.waimaiNg.dish.dishSkuSellStatus.FoodData;
import com.meituan.sdk.model.waimaiNg.dish.foodBatchGet.FoodBatchGetResponse;
import com.meituan.sdk.model.waimaiNg.dish.foodQueryList.FoodInfo;

import java.util.List;

/**
 * About:
 * Other:
 * Created: wfli on 2023/7/5 14:29.
 * Editored:
 */
public interface MtSdkDishService {

    /**
     * 7.2.5查询菜品分类
     *
     * @return
     */
    List<DishCategoryInfo> queryCatList();

    /**
     * 7.2.6批量上传／更新菜品
     *
     * @param ePoiId ERP方门店id 最大长度100
     * @param dishes 菜品列表json字符串， 参考dish说明
     * @return
     */
    String dishBatchUpload(String ePoiId, List<DishInfo> dishes);

    /**
     * 7.2.7.更新菜品价格【sku的价格】
     *
     * @param ePoiId        ERP方门店id 最大长度100
     * @param dishSkuPrices 菜品sku价格
     * @return
     */
    String dishUpdatePrice(String ePoiId, String dishSkuPrices);

    /**
     * 7.2.14.查询菜品属性
     *
     * @param eDishCode ERP方菜品id
     * @return
     */
    List<DishPropertyInfo> queryPropertyList(String eDishCode);

    /**
     * 7.2.22.查询门店菜品列表
     *
     * @return
     */
    List<FoodInfo> queryFoodList();

    /**
     * 7.2.27.批量更新菜品售卖状态
     *
     * @param sellStatus 售卖状态，1表下架，0表上架
     * @param foodData   菜品sku集合
     * @return
     */
    String dishSkuSellStatus(Integer sellStatus, List<FoodData> foodData);

    /**
     * 7.2.32.查询门店菜品列表（包括套餐商品和普通商品）
     *
     * @param offset
     * @param limit
     * @return
     */
    List<com.meituan.sdk.model.waimaiNg.dish.dishFoodListAll.FoodInfo> dishFoodListAll(int offset, int limit);

    /**
     * 7.2.33.批量查询门店菜品（包括套餐商品和普通商品）
     *
     * @param appFoodCodes
     * @return
     */
    FoodBatchGetResponse foodBatchGet(String appFoodCodes);
}
