package com.heart.ticket.base.utils;

import cn.hutool.http.HttpUtil;

/**
 * @author wfli
 * @since 2024/6/5 09:18
 */
public class DlUtils {

    public static void main(String[] args) {

        String base = "http://172.16.143.108:8080/img";
        String uri = "/termMenu/10000050/8b1a3bb358ed471bb4bf76fee5618642.png";
        String url = base + uri;
        String path = "D:\\02-项目资料\\09-银医医保软件平台\\02-河南分公司源码\\菜单图片\\";
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        dl(url, path + fileName);
    }

    public static void dl(String url,String target){
        HttpUtil.downloadFile(url, target);
        System.out.println("下载完成!");
    }
}
