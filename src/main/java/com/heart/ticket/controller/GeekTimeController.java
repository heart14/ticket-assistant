package com.heart.ticket.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.heart.ticket.base.model.SysResponse;
import com.heart.ticket.service.geek.GeekTimeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author wfli
 * @since 2024/5/8 14:33
 */
@Api(tags = "极客时间downloader")
@Slf4j
@RestController
@RequestMapping("/geek")
public class GeekTimeController {

    private final GeekTimeService geekTimeService;

    public GeekTimeController(GeekTimeService geekTimeService) {
        this.geekTimeService = geekTimeService;
    }

    //@GetMapping("/download")
    public SysResponse download(String something) {

        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(Paths.get("C:\\weblogic\\logs\\data.json")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return SysResponse.fail(e.getMessage());
        }
        JSONObject entries = JSONUtil.parseObj(jsonString);
        JSONArray list = entries.getJSONArray("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject chapter = list.getJSONObject(i);
            Integer chapterId = chapter.getInt("id");
            String title = chapter.getStr("title");
            Integer count = chapter.getInt("count");
            System.out.println(title + " id[" + chapterId + "]" + " 共" + count + "讲");
            JSONArray articleList = chapter.getJSONArray("article_list");
            for (int j = 0; j < articleList.size(); j++) {
                // 从article_list中遍历获取每一讲的详细信息
                // 每一讲的详细信息里包含了 基本信息、author作者、article文章、chapter所属章节、audio音频、video视频、files文件、extra其它
                JSONObject detail = articleList.getJSONObject(j);
                // 基本信息
                String detailId = detail.getStr("id");
                String time = detail.getStr("time");
                String columnTitle = detail.getStr("column_title");
                String productType = detail.getStr("product_type");
                // 归档
                String archive = "C:\\weblogic\\logs\\" + columnTitle + "\\archive";
                File archiveDir = new File(archive);
                archiveDir.mkdirs();
                new File(archive + "\\" + "article").mkdirs();
                new File(archive + "\\" + "audio").mkdirs();
                new File(archive + "\\" + "video").mkdirs();
                //课程目录
                String s = "C:\\weblogic\\logs\\";
                // article文章
                JSONObject article = detail.getJSONObject("article");
                String articleTitle = article.getStr("title");
                String contentMd = article.getStr("content_md");
                System.out.println("    " + articleTitle + " id[" + detailId + "]");
                String articleTitlePathName = articleTitle.replaceAll("[<>:\"/\\\\|?*｜]", " ");
                if (StrUtil.isNotBlank(contentMd)) {
                    //文章目录 根目录 + 课程目录 + 章节目录 + 讲目录带id + 文章、音频、视频
                    String articlePath = s + columnTitle + "\\" + title + "\\" + articleTitlePathName + " " + detailId + "\\" + "article";
                    File articleDir = new File(articlePath);
                    if (!articleDir.exists()) {
                        boolean mkdirs = articleDir.mkdirs();
                        if (!mkdirs) {
                            log.error("dir create fail :{}", articlePath);
                        }
                    }
                    // 保存到MD文件
                    String articleFile = articlePath + "\\" + articleTitlePathName + ".md";
                    try {
                        // 创建 BufferedWriter 对象以将文本写入文件
                        BufferedWriter writer = new BufferedWriter(new FileWriter(articleFile));
                        // 写入 Markdown 字符串到文件
                        writer.write(contentMd);
                        // 关闭写入器
                        writer.close();
                        // 打印
                        System.out.println("        Markdown 文件下载完成");
                        // 归档
                        // 源文件路径
                        Path sourcePath = Paths.get(articleFile);
                        // 目标文件路径
                        Path targetPath = Paths.get(archive + "\\" + "article" + "\\" + articleTitlePathName + ".md");
                        try {
                            Files.copy(sourcePath, targetPath);
                        } catch (IOException e) {
                            System.out.println("        Markdown 归档失败：" + e.getMessage());
                        }

                    } catch (IOException e) {
                        System.out.println("        " + articleTitle + " id[" + detailId + "]Markdown 文件下载失败：" + e.getMessage());
                    }
                } else {
                    System.out.println("        没有图文内容，无需下载");
                }
                // audio音频
                JSONObject audio = detail.getJSONObject("audio");
                String audioId = audio.getStr("id");
                String downloadUrl = audio.getStr("download_url");
                if (StrUtil.isBlank(downloadUrl)) {
                    // 没有音频
                    System.out.println("        没有音频内容，无需下载");
                } else {
                    //文章目录 根目录 + 课程目录 + 章节目录 + 讲目录带id + 文章、音频、视频
                    String audioPath = s + columnTitle + "\\" + title + "\\" + articleTitlePathName + " " + detailId + "\\" + "audio";
                    File audioDir = new File(audioPath);
                    if (!audioDir.exists()) {
                        boolean mkdirs = audioDir.mkdirs();
                        if (!mkdirs) {
                            log.error("dir create fail :{}", audioPath);
                        }
                    }
                    // 下载音频
                    String audioFile = audioPath + "\\" + articleTitlePathName + ".mp3";
                    HttpUtil.downloadFile(downloadUrl, audioFile);
                    System.out.println("        音频 下载完成");
                    // 归档
                    // 源文件路径
                    Path sourcePath = Paths.get(audioFile);
                    // 目标文件路径
                    Path targetPath = Paths.get(archive + "\\" + "audio" + "\\" + articleTitlePathName + ".mp3");
                    try {
                        Files.copy(sourcePath, targetPath);
                    } catch (IOException e) {
                        System.out.println("        音频 归档失败：" + e.getMessage());
                    }
                }
                // video视频
                JSONObject video = detail.getJSONObject("video");
                String videoId = video.getStr("id");
                String u = video.getStr("url");
                if (StrUtil.isBlank(videoId) || StrUtil.isBlank(u)) {
                    // 没有视频
                    System.out.println("        没有视频内容，无需下载");
                } else {
                    String url = "https://media001.geekbang.org/" + u;
                    Integer width = video.getInt("width");
                    Integer height = video.getInt("height");
                    Long size = video.getLong("size");
                    String videoTime = video.getStr("time");
                    //文章目录 根目录 + 课程目录 + 章节目录 + 讲目录带id + 文章、音频、视频
                    String videoPath = s + columnTitle + "\\" + title + "\\" + articleTitlePathName + " " + detailId + "\\" + "video";
                    File videoDir = new File(videoPath);
                    if (!videoDir.exists()) {
                        boolean mkdirs = videoDir.mkdirs();
                        if (!mkdirs) {
                            log.error("dir create fail :{}", videoPath);
                        }
                    }
                    // 下载视频
                    String videoFile = videoPath + "\\" + articleTitlePathName + ".mp4";
                    HttpUtil.downloadFile(url, videoFile);
                    System.out.println("        视频 下载完成" + " video id[" + videoId + "]" + " 时长" + videoTime + " 大小" + size + " 链接" + url);
                    // 归档
                    // 源文件路径
                    Path sourcePath = Paths.get(videoFile);
                    // 目标文件路径
                    Path targetPath = Paths.get(archive + "\\" + "video" + "\\" + articleTitlePathName + ".mp4");
                    try {
                        Files.copy(sourcePath, targetPath);
                    } catch (IOException e) {
                        System.out.println("        视频 归档失败：" + e.getMessage());
                    }
                }
            }
            System.out.println("============");
        }
        return SysResponse.success();
    }

    @GetMapping("/download")
    public SysResponse geekTime(String cellphone,String password){
        geekTimeService.geekTime( cellphone, password);
        return SysResponse.success();
    }

    @GetMapping("/sku/{skuId}")
    public SysResponse geekTime(@PathVariable("skuId") long skuId){
        geekTimeService.geekTime(skuId);
        return SysResponse.success();
    }
}
