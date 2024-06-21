package com.heart.ticket.controller;

import com.alibaba.fastjson.JSONArray;
import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.base.model.query.ListQuery;
import com.heart.ticket.service.mt.MtSdkDishService;
import com.meituan.sdk.model.waimaiNg.dish.dishQueryListByEpoiid.DishInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * About:
 * Other:
 * Created: wfli on 2023/5/16 15:19.
 * Editored:
 */
@Api(tags = "美团sdk api")
@Slf4j
@RestController
@RequestMapping("/mt/dish")
public class DemoController {

    private final MtSdkDishService mtSdkDishService;

    public DemoController(MtSdkDishService mtSdkDishService) {
        this.mtSdkDishService = mtSdkDishService;
    }

    @ApiOperation("美团查询分类")
    @PostMapping("/queryCatList")
    public SysResponse queryCatList(@RequestBody Map<String, Object> map) {
        return SysResponse.success(mtSdkDishService.queryCatList());
    }

    @ApiOperation("美团查询菜品")
    @PostMapping("/queryFoodList")
    public SysResponse queryFoodList(@RequestBody Map<String, Object> map) {
        return SysResponse.success(mtSdkDishService.queryFoodList());
    }

    @ApiOperation("美团查询属性")
    @PostMapping("/queryPropertyList")
    public SysResponse queryPropertyList(@RequestBody Map<String, Object> map) {
        String eDishCode = String.valueOf(map.get("eDishCode"));
        return SysResponse.success(mtSdkDishService.queryPropertyList(eDishCode));
    }

    @ApiOperation("美团批量新增菜品")
    @PostMapping("/dishBatchUpload")
    public SysResponse dishBatchUpload(@RequestBody Map<String, Object> map) {
        String ePoiId = String.valueOf(map.get("ePoiId"));
        String dishes = String.valueOf(map.get("dishes"));
        List<DishInfo> parseArray = JSONArray.parseArray(dishes, DishInfo.class);
        return SysResponse.success(mtSdkDishService.dishBatchUpload(ePoiId, parseArray));
    }

    @PostMapping("/list")
    public SysResponse listTest(@RequestBody ListQuery query){
        log.info("query:{}", query);
        return SysResponse.success(query);
    }

}
