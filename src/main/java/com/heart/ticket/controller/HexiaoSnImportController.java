package com.heart.ticket.controller;

import com.alibaba.excel.EasyExcel;
import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.base.model.hexiao.SnInfo;
import com.heart.ticket.listener.HexiaoSnImportListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wfli
 * @since 2024/6/28 14:11
 */
@RestController
@RequestMapping("/importSn")
public class HexiaoSnImportController {

    /**
     * 使用方法：
     *   启动应用，postman请求接口，参数传递excel文件路径
     *   执行完毕得到控制台打印内容
     *   到测试环境服务器vim xx.sh
     *   把打印内容粘贴，保存，执行脚本即可
     */
    @PostMapping("/filePath")
    public SysResponse build(String filePath) {

        System.out.println("#!/bin/sh\n" +
                "\n" +
                "curl -X GET 'https://ecard.chinaums.com/hexiao/ums.meituan/heartbeat'");
        EasyExcel.read(filePath, SnInfo.class, new HexiaoSnImportListener()).sheet().doRead();

        return SysResponse.success();
    }
}
