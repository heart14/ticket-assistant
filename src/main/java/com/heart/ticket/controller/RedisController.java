package com.heart.ticket.controller;

import com.heart.ticket.base.common.Constants;
import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.base.model.redis.AddParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wfli
 * @since 2024/7/24 16:22
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/add")
    public SysResponse redisAdd(@RequestBody AddParam param) {
        redisTemplate.opsForValue().set(param.getKey(), param.getValue(), Constants.REDIS_CACHE_ENTRY_TTL, TimeUnit.MILLISECONDS);
        return SysResponse.success();
    }

    @DeleteMapping("/del/{key}")
    public SysResponse redisDelete(@PathVariable("key") String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(hasKey)) {
            redisTemplate.delete(key);
        }
        return SysResponse.success();
    }

    @PutMapping("/update")
    public SysResponse redisUpdate(@RequestBody AddParam param) {
        Boolean hasKey = redisTemplate.hasKey(param.getKey());
        if (Boolean.TRUE.equals(hasKey)) {
            redisTemplate.opsForValue().set(param.getKey(), param.getValue(), Constants.REDIS_CACHE_ENTRY_TTL, TimeUnit.MILLISECONDS);
        }
        return SysResponse.success();
    }

    @GetMapping("/key/{key}")
    public SysResponse redisKey(@PathVariable("key") String key) {
        String value = redisTemplate.opsForValue().get(key);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put(key, value);
        return SysResponse.success(stringStringHashMap);
    }

    @GetMapping("/keys")
    public SysResponse redisKeys() {
        Set<String> keys = redisTemplate.keys("*");
        List<HashMap<String, String>> mapList = new ArrayList<>();
        if (keys != null) {
            for (String key : keys) {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                String value = redisTemplate.opsForValue().get(key);

                stringStringHashMap.put(key, value);
                mapList.add(stringStringHashMap);
            }
        }
        return SysResponse.success(mapList);
    }
}
