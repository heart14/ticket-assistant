package com.heart.ticket.service.geek.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.heart.ticket.base.model.HttpResult;
import com.heart.ticket.base.model.geek.Course;
import com.heart.ticket.base.model.geek.Detail;
import com.heart.ticket.base.model.geek.Lesson;
import com.heart.ticket.base.model.geek.course.CourseInfo;
import com.heart.ticket.base.model.geek.lesson.*;
import com.heart.ticket.base.utils.HttpUtils;
import com.heart.ticket.service.geek.GeekTimeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wfli
 * @since 2024/5/8 17:58
 */
@Slf4j
@Service
public class GeekTimeServiceImpl implements GeekTimeService {

    /**
     * 通过login接口可获取cookie
     */
    public static final String COOKIE = "GCID=2891c41-a128d74-42cb21c-9f02a0f;GRID=2891c41-a128d74-42cb21c-9f02a0f;GCESS=BgMEeoc_ZgwBAQgBAwYEzUusvgsCBgAJAREBCPN1FQAAAAAAAgR6hz9mCgQAAAAADQEBBQQAAAAABAQAjScABwR8jNZX;gksskpitn=568be447-ab4a-4c69-a87f-b55d7533c3dd;GRID=1d7c67a-771ebfc-de6c71a-c93dc5c;SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1715439482|1715439482";
    public static final String BASE_DOWNLOAD_PATH = "D:\\98-极客时间";
    /**
     * 音频文件下载控制：0正常下载，1空文件占位，2不下载
     */
    public static final int AUDIO_DL_FLAG = 0;
    /**
     * 视频文件下载控制：0正常下载，1空文件占位，2不下载
     */
    public static final int VIDEO_DL_FLAG = 0;

    @Override
    public void login(String cellphone, String password) {
        String url = "https://account.geekbang.org/account/ticket/login";
        Map<String, Object> param = new HashMap<>();
        param.put("platform", 3);
        param.put("appid", 17);
        param.put("remember", 1);
        param.put("source", "");
        param.put("ucode", "");
        param.put("country", 86);
        param.put("cellphone", cellphone);
        param.put("password", password);

        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        headers.put("Origin", "https://b.geekbang.org");
        headers.put("Content-Type", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = HttpUtils.httpPost(url, JSONUtil.toJsonStr(param), headers);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Header[] responseHeaders = response.getHeaders("Set-cookie");
        StringBuilder sb = new StringBuilder();
        for (Header responseHeader : responseHeaders) {
            String headerValue = responseHeader.getValue();
            String split = headerValue.split(";")[0];
            String[] cookies = split.split("=");

            sb.append(cookies[0]).append("=").append(cookies[1]).append(";");
        }
        // 删除末尾的 ";"
        sb.deleteCharAt(sb.length() - 1);
        System.out.println("cookie:" + sb.toString());
    }

    /**
     * 设置较大的pageSize，一把查询完所有课程
     */
    @Override
    public void geekTime() {
        try {
            Course course = getCourse();
            if (course == null) {
                return;
            }
            Map<String, Object> failList = new HashMap<>();
            // 处理页面所有课程集合
            pageProcess(course, failList);
            System.out.println("下载完成！开始重试...");
            // 进行失败列表重试
            failListProcess(failList);
            System.out.println("下载任务结束！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理失败列表重试
     * @param failList
     */
    private void failListProcess(Map<String, Object> failList) {
        List<Long> fail_lesson = (List<Long>) failList.get("fail_lesson");
        List<String> fail_article = (List<String>) failList.get("fail_article");

        retryFailLesson(fail_lesson);
        retryFailArticle(fail_article);
    }

    /**
     * 重新下载失败课程
     * @param fail_lesson
     */
    private void retryFailLesson(List<Long> fail_lesson) {
        if (fail_lesson == null) {
            return;
        }
        try {
            for (Long sku : fail_lesson) {
                Lesson lesson = null;
                try {
                    lesson = getArticle(sku);
                } catch (Exception e) {
                    // 只重试这一次，再失败不管了
                    System.err.println("    retry sku:" + sku + "课程信息获取失败，请关注");
                }
                if (lesson == null) {
                    continue;
                }
                System.out.println("retry****" + sku);
                // 课程内包含的所有章节集合
                List<ChapterInfo> chapterList = lesson.getList();
                // 遍历获取每个章节
                for (ChapterInfo chapterInfo : chapterList) {
                    System.out.println("  retry|--" + chapterInfo.getTitle());
                    // 章节内包含的所有讲集合
                    List<ArticleInfo> articleList = chapterInfo.getArticle_list();
                    // 遍历获取每一讲的内容
                    for (ArticleInfo info : articleList) {
                        Detail detail = null;
                        try {
                            detail = getDetail(info.getId());
                        } catch (Exception e) {
                            // 只重试这一次，再失败不管了
                            System.err.println("    retry detailId:" + info.getId() + "讲信息获取失败，请关注");
                        }
                        if (detail == null) {
                            continue;
                        }
                        // 下载
                        try {
                            dlContent(detail);
                            dlAudio(detail);
                            dlVideo(detail);
                        } catch (Exception e) {
                            // 只重试这一次，再失败不管了
                            System.err.println("    retry" + detail.getArticle().getTitle() + "讲内容下载失败，请关注");
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 只重试这一次，再失败不管了
        }

    }

    /**
     * 重新下载失败讲
     * @param fail_article
     */
    private void retryFailArticle(List<String> fail_article) {
        if (fail_article == null) {
            return;
        }
        for (String id : fail_article) {
            Detail detail = null;
            try {
                detail = getDetail(id);
            } catch (Exception e) {
                System.err.println("    retry detailId:" + id + "讲信息获取失败，请关注");
            }
            if (detail == null) {
                continue;
            }
            // 下载
            try {
                dlContent(detail);
                dlAudio(detail);
                dlVideo(detail);
            } catch (Exception e) {
                System.err.println("    retry" + detail.getArticle().getTitle() + "讲内容下载失败，请关注");
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理课程下载
     *
     * @param course
     * @throws IOException
     * @throws ParseException
     */
    private void pageProcess(Course course, Map<String, Object> failList) throws IOException, ParseException {
        List<Long> fail_lesson = new ArrayList<>();
        List<String> fail_article = new ArrayList<>();

        // 账号下所有课程集合
        List<CourseInfo> courseList = course.getList();
        for (CourseInfo courseInfo : courseList) {
            System.out.println("****" + courseInfo.getTitle());
            // 遍历获取每一门课程
            Lesson lesson = null;
            try {
                lesson = getArticle(courseInfo.getSku());
            } catch (Exception e) {
                System.err.println("    sku:" + courseInfo.getSku() + "课程信息获取失败，请关注");
                //failList
                fail_lesson.add(courseInfo.getSku());
            }
            if (lesson == null) {
                continue;
            }
            // 课程内包含的所有章节集合
            List<ChapterInfo> chapterList = lesson.getList();
            // 遍历获取每个章节
            for (ChapterInfo chapterInfo : chapterList) {
                System.out.println("  |--" + chapterInfo.getTitle());
                // 章节内包含的所有讲集合
                List<ArticleInfo> articleList = chapterInfo.getArticle_list();
                // 遍历获取每一讲的内容
                for (ArticleInfo info : articleList) {
                    Detail detail = null;
                    try {
                        detail = getDetail(info.getId());
                    } catch (Exception e) {
                        System.err.println("    detailId:" + info.getId() + "讲信息获取失败，请关注");
                        //failList
                        // 捕获异常，保存到失败列表，然后继续下载下一个内容，不要中断程序
                        //  失败列表要分别存储：
                        //  失败的课程列表/course/articles  查询参数courseInfo.getSku()
                        //  失败的讲列表/article/detail  查询参数info.getId()
                        //  然后进行重试处理
                        //  处理时，对失败的课程，整个课程重新下载，对失败的讲，只重新下载单个讲
                        fail_article.add(info.getId());
                    }
                    if (detail == null) {
                        continue;
                    }
                    // 下载
                    try {
                        dlContent(detail);
                        dlAudio(detail);
                        dlVideo(detail);
                    } catch (Exception e) {
                        System.err.println("    " + detail.getArticle().getTitle() + "讲内容下载失败，请关注");
                        e.printStackTrace();
                        //failList 某个课程的内容下载失败了，也要保存到失败列表，然后进行重试
                        fail_article.add(info.getId());
                    }
                }
            }
            failList.put("fail_lesson", fail_lesson);
            failList.put("fail_article", fail_article);
            System.out.println("============");
        }
    }

    /**
     * 下载markdown
     *
     * @param detail
     */
    private void dlContent(Detail detail) {
        String columnTitle = detail.getColumn_title();//课程名
        String title = detail.getChapter().getTitle();//章节名
        // 每一讲 content、audio、video等
        ArticleInfoArticle article = detail.getArticle();
        System.out.println("    |--" + article.getTitle());

        String downloadContentMd = article.getContent_md();
        if (StrUtil.isEmpty(downloadContentMd)) {
            return;
        }

        String articleTitle = article.getTitle().replaceAll("[\\\\/:*?\"<>｜|\\s]", "");//讲名
        String fileName = articleTitle + ".md";
        String downloadPath = BASE_DOWNLOAD_PATH + "\\" + columnTitle + "\\" + title + "\\" + articleTitle + "\\";
        String archivePath = BASE_DOWNLOAD_PATH + "\\" + columnTitle + "\\archive\\markdown\\";

        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        File archiveDir = new File(archivePath);
        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
        }

        // 下载markdown中的图片到本地，并将图片地址替换为本地图片
        downloadContentMd = dlContentImage(archivePath, downloadContentMd);
        // 下载markdown
        try {
            // 创建 BufferedWriter 对象以将文本写入文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(downloadPath + fileName));
            // 写入 Markdown 字符串到文件
            writer.write(downloadContentMd);
            // 关闭写入器
            writer.close();
            // 打印
            System.out.println("      " + fileName + "下载完成");
            // 归档
            // 源文件路径
            Path sourcePath = Paths.get(downloadPath + fileName);
            // 目标文件路径
            Path targetPath = Paths.get(archivePath + fileName);
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                if (e instanceof FileAlreadyExistsException) {
                    System.err.println("      " + fileName + "归档失败：文件已存在");
                } else {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("      " + fileName + "下载失败：" + e.getMessage());
        }
    }

    /**
     * 下载markdown内图片
     *
     * @param archivePath 图片归档目录
     * @param contentMd   内容
     * @return
     */
    private String dlContentImage(String archivePath, String contentMd) {
        String imagePath = archivePath.replace("markdown", "image");
        File imageDir = new File(imagePath);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        // 定义图片链接匹配模式
        String imageUrlPattern = "!\\[.*?\\]\\((https://static001\\.geekbang\\.org/.*?)\\)";
        Pattern urlPattern = Pattern.compile(imageUrlPattern);
        Matcher urlMatcher = urlPattern.matcher(contentMd);

        // 用于生成替换图片链接后的content
        StringBuffer result = new StringBuffer();
        // 寻找并提取所有匹配的图片链接
        while (urlMatcher.find()) {
            String imageUrl = urlMatcher.group(1);
            // 定义正则表达式匹配文件名
            Pattern namePattern = Pattern.compile("/([^/]+)\\.(jpg|png|jpeg|bmp|gif)");
            Matcher nameMatcher = namePattern.matcher(imageUrl);
            // 查找文件名
            String imageName;
            if (nameMatcher.find()) {
                imageName = nameMatcher.group(1) + "." + nameMatcher.group(2);
            } else {
                imageName = RandomUtil.randomString(32);
            }
            String localImagePath = imagePath + imageName;
            // 下载图片  todo 图片下载失败？
            HttpUtil.downloadFile(imageUrl, localImagePath);
            // 将markdown中的图片url替换为本地url
            urlMatcher.appendReplacement(result, "![](" + localImagePath.replace("\\", "/") + ")");
        }
        urlMatcher.appendTail(result);
        return result.toString();
    }

    /**
     * 下载音频
     *
     * @param detail
     */
    private void dlAudio(Detail detail) {
        String columnTitle = detail.getColumn_title();//课程名
        String title = detail.getChapter().getTitle();//章节名
        // 每一讲 content、audio、video等
        ArticleInfoArticle article = detail.getArticle();
        ArticleInfoAudio audio = detail.getAudio();

        String downloadUrl = audio.getDownload_url();
        if (StrUtil.isEmpty(downloadUrl)) {
            System.out.println("      没有音频内容，无需下载");
            return;
        }

        String articleTitle = article.getTitle().replaceAll("[\\\\/:*?\"<>｜|\\s]", "");//讲名
        String fileName = articleTitle + ".mp3";
        String downloadPath = BASE_DOWNLOAD_PATH + "\\" + columnTitle + "\\" + title + "\\" + articleTitle + "\\";
        String archivePath = BASE_DOWNLOAD_PATH + "\\" + columnTitle + "\\archive\\audio\\";

        // 处理下载
        processDownload(downloadUrl, fileName, downloadPath, archivePath, AUDIO_DL_FLAG);
    }

    /**
     * 下载视频
     *
     * @param detail
     */
    private void dlVideo(Detail detail) {
        String columnTitle = detail.getColumn_title();//课程名
        String title = detail.getChapter().getTitle();//章节名
        // 每一讲 content、audio、video等
        ArticleInfoArticle article = detail.getArticle();
        ArticleInfoVideo video = detail.getVideo();

        String url = video.getUrl();
        if (StrUtil.isEmpty(url)) {
            System.out.println("      没有视频内容，无需下载");
            return;
        }
        String downloadUrl = "https://media001.geekbang.org/" + url;

        String articleTitle = article.getTitle().replaceAll("[\\\\/:*?\"<>｜|\\s]", "");//讲名
        String fileName = articleTitle + ".mp4";
        String downloadPath = BASE_DOWNLOAD_PATH + "\\" + columnTitle + "\\" + title + "\\" + articleTitle + "\\";
        String archivePath = BASE_DOWNLOAD_PATH + "\\" + columnTitle + "\\archive\\video\\";
        // 处理下载
        processDownload(downloadUrl, fileName, downloadPath, archivePath, VIDEO_DL_FLAG);
    }

    /**
     * 获取账号下所有课程信息
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private Course getCourse() throws IOException, ParseException {
        String url = "https://b.geekbang.org/app/v1/user/center/course";
        String json = "{\"course_type\":0,\"learn_status\":0,\"expire_status\":1,\"hide_status\":1,\"order_by\":\"learn\",\"sort\":\"\",\"total\":0,\"page\":1,\"size\":15}";
        // 获取账号下所有课程信息
        HttpResult result = reqGeekTime(url, json);
        if (result.getHttpStatus() != 200) {
            return null;
        }
        JSONObject resultObject = JSONUtil.parseObj(result.getHttpResult());
        return JSONUtil.toBean(resultObject.getStr("data"), Course.class);
    }

    /**
     * 获取每一个课程信息
     *
     * @param id
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private Lesson getArticle(long id) throws IOException, ParseException {
        String url = "https://b.geekbang.org/app/v1/course/articles";
        String json = "{\"id\":" + id + "}";
        // 获取每一个课程信息
        HttpResult result = reqGeekTime(url, json);
        if (result.getHttpStatus() != 200) {
            return null;
        }
        // 解析article
        JSONObject resultObject = JSONUtil.parseObj(result.getHttpResult());
        return JSONUtil.toBean(resultObject.getStr("data"), Lesson.class);
    }

    /**
     * 获取课程章节里每一讲的信息
     *
     * @param id
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private Detail getDetail(String id) throws IOException, ParseException {
        String url = "https://b.geekbang.org/app/v1/article/detail";
        String json = "{\"article_id\":\"" + id + "\"}";
        // 获取课程章节里每一讲的信息
        HttpResult result = reqGeekTime(url, json);
        if (result.getHttpStatus() != 200) {
            return null;
        }
        JSONObject resultObject = JSONUtil.parseObj(result.getHttpResult());
        return JSONUtil.toBean(resultObject.getStr("data"), Detail.class);
    }

    /**
     * 请求极客时间API接口
     *
     * @param api   接口地址
     * @param param 接口参数
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private HttpResult reqGeekTime(String api, String param) throws IOException, ParseException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.put("Host", "b.geekbang.org");
        headers.put("Origin", "https://b.geekbang.org");
        headers.put("Connection", "keep-alive");
        headers.put("Cookie", COOKIE);
        return HttpUtils.post(api, param, headers);
    }

    /**
     * 处理音频&视频文件下载
     *
     * @param downloadUrl  文件下载地址
     * @param fileName     文件名
     * @param downloadPath 下载目录
     * @param archivePath  归档目录
     * @param processFlag  处理标识：0正常下载，1空文件占位，2不下载
     */
    private void processDownload(String downloadUrl, String fileName, String downloadPath, String archivePath, int processFlag) {
        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        //下载
        switch (processFlag) {
            case 0:
                HttpUtil.downloadFile(downloadUrl, downloadPath + fileName);
                System.out.println("      " + fileName + "下载完成");
                break;
            case 1:
                tempFile(downloadPath + fileName);
                break;
            default:
                return;
        }
        // 归档
        File archiveDir = new File(archivePath);
        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
        }
        // 源文件路径
        Path sourcePath = Paths.get(downloadPath + fileName);
        // 目标文件路径
        Path targetPath = Paths.get(archivePath + fileName);
        try {
            Files.copy(sourcePath, targetPath);
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                System.err.println("      " + fileName + "归档失败：文件已存在");
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建占位空文件，用于测试，不实际下载音频视频
     *
     * @param filePath
     */
    private void tempFile(String filePath) {
        // 创建 File 对象
        File file = new File(filePath);
        try {
            // 创建空白文件
            if (file.createNewFile()) {
                System.out.println("      临时文件：" + filePath);
            } else {
                System.out.println("      临时文件已存在，不要重复创建：" + filePath);
            }
            // 关闭文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            fos.close();
        } catch (IOException e) {
            System.out.println("      新建临时文件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**-------------------------------------------------------------**/


    /**
     * 根据当前页与总页数关系，循环查询完所有课程
     * 不要使用递归，大数据量的时候可能会堆栈溢出
     */
    @Override
    public void geekTimeCyclic() {
        int page = 1;
        int size = 10;
        try {
            while (true) {
                Course course = getCourse(page, size);
                if (course == null || course.getList() == null || course.getList().size() == 0) {
                    // 没有数据，退出循环
                    break;
                }
                Map<String, Object> failList = new HashMap<>();
                pageProcess(course, failList);
                if (course.getPage().getTotal_page() == course.getPage().getPage()) {
                    // 当前页数与总页数相等，说明是最后一页了，退出循环
                    break;
                } else {
                    // 否则，page+1，继续查询下一页数据
                    page += 1;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取账号下所有课程信息
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private Course getCourse(int page, int size) throws IOException, ParseException {
        String url = "https://b.geekbang.org/app/v1/user/center/course";
        String json = "{\"course_type\":0,\"learn_status\":0,\"expire_status\":1,\"hide_status\":1,\"order_by\":\"learn\",\"sort\":\"\",\"total\":0,\"page\":" + page + ",\"size\":" + size + "}";
        // 获取账号下所有课程信息
        HttpResult result = reqGeekTime(url, json);
        if (result.getHttpStatus() != 200) {
            return null;
        }
        JSONObject resultObject = JSONUtil.parseObj(result.getHttpResult());
        return JSONUtil.toBean(resultObject.getStr("data"), Course.class);
    }
}