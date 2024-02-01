package com.heart.ticket.controller;

import com.heart.ticket.base.enums.RespCode;
import com.heart.ticket.base.exceptions.BizException;
import com.heart.ticket.base.exceptions.SysException;
import com.heart.ticket.base.model.SysResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * About:
 * Other:
 * Created: wfli on 2023/8/31 13:30.
 * Editored:
 */
@Api(tags = "测试api")
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试接口
     *
     * @param p
     * @return
     */
    @ApiOperation("测试接口")
    @GetMapping("/api/{p}")
    public SysResponse testApi(@PathVariable("p") String p) {
        return SysResponse.success(p);
    }

    /**
     * 测试异常处理
     */
    @ApiOperation("测试抛出异常")
    @GetMapping("/api/exception")
    public SysResponse testException(int t) {
        if (t == 0) {
            throw new SysException(RespCode.SYS_EXCEPTION.getCode(), RespCode.SYS_EXCEPTION.getMsg());
        } else if (t == 1) {
            throw new BizException(RespCode.BIZ_FAIL.getCode(), RespCode.BIZ_FAIL.getMsg());
        } else {

        }
        return SysResponse.success(t);
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public SysResponse upload(MultipartFile file) {
        log.info("file:{}", file);
        log.info("file.name:{}", file.getOriginalFilename());
        return SysResponse.success();
    }

    /**
     * url短链转长链
     *
     * @param url
     * @return
     */
    @ApiOperation("url短链转长链")
    @PostMapping("/http")
    public SysResponse httpGet(String url) {
        try {
            log.info("url:{}", url);
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            int responseCode = connection.getResponseCode();
            String realUrl = connection.getURL().toString();
            log.info("real Url:{}", realUrl);
            connection.disconnect();

            String regEx = "(\\?|&+)(.+?)=([^&]*)";//匹配参数名和参数值的正则表达式
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(realUrl);
            // LinkedHashMap是有序的Map集合，遍历时会按照加入的顺序遍历输出
            Map<String, String> paramMap = new LinkedHashMap<>();
            while (m.find()) {
                String paramName = m.group(2);//获取参数名
                String paramVal = m.group(3);//获取参数值
                paramMap.put(paramName, paramVal);
            }
            return SysResponse.success(paramMap);
        } catch (IOException e) {
            throw new SysException(RespCode.BIZ_INVALID.getCode(), RespCode.BIZ_INVALID.getMsg());
        }
    }

}
