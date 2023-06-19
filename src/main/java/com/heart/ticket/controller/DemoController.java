package com.heart.ticket.controller;

import com.heart.ticket.base.common.SystemProperties;
import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.service.MtSdkDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * About:
 * Other:
 * Created: wfli on 2023/5/16 15:19.
 * Editored:
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    public static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final MtSdkDemoService mtSdkDemoService;

    public DemoController(MtSdkDemoService mtSdkDemoService) {
        this.mtSdkDemoService = mtSdkDemoService;
    }

    @PostMapping("/queryCatList")
    public SysResponse queryCatList(@RequestBody Map<String, Object> map) {
        Long developerId = Long.valueOf(String.valueOf(map.get("developerId")));
        String signKey = String.valueOf(map.get("signKey"));
        String appAuthToken = String.valueOf(map.get("appAuthToken"));
        return SysResponse.success(mtSdkDemoService.queryCatList(developerId, signKey, appAuthToken));
    }

    @PostMapping("/queryFoodList")
    public SysResponse queryFoodList(@RequestBody Map<String, Object> map) {
        Long developerId = Long.valueOf(String.valueOf(map.get("developerId")));
        String signKey = String.valueOf(map.get("signKey"));
        String appAuthToken = String.valueOf(map.get("appAuthToken"));
        return SysResponse.success(mtSdkDemoService.queryFoodList(developerId, signKey, appAuthToken));
    }

    @PostMapping("/queryPropertyList")
    public SysResponse queryPropertyList(@RequestBody Map<String, Object> map) {
        Long developerId = Long.valueOf(String.valueOf(map.get("developerId")));
        String signKey = String.valueOf(map.get("signKey"));
        String appAuthToken = String.valueOf(map.get("appAuthToken"));
        String eDishCode = String.valueOf(map.get("eDishCode"));
        return SysResponse.success(mtSdkDemoService.queryPropertyList(developerId, signKey, appAuthToken,eDishCode));
    }

}
