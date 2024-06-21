package com.heart.ticket.service.geek.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.internal.util.StringUtils;
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
//    public String COOKIE = "GCID=b08b131-11a42f5-a92a71a-ef78d74;GRID=b08b131-11a42f5-a92a71a-ef78d74;GCESS=BgEI83UVAAAAAAANAQEJAREEBACNJwAMAQEIAQMLAgYAAgRrGkdmBQQAAAAABgQ8FZJeAwRrGkdmBwSpG4qRCgQAAAAA;gksskpitn=3b6825ff-fd81-4189-92e7-bb177105cbdd;GRID=f94842b-1d206dc-cac3b8d-4d951ab;SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1715935851|1715935851";
    public String COOKIE = "";
    public static final String BASE_DOWNLOAD_PATH = "D:\\98-极客时间\\李文飞";
    /**
     * 音频文件下载控制：0正常下载，1空文件占位，2不下载
     */
    public static final int AUDIO_DL_FLAG = 1;
    /**
     * 视频文件下载控制：0正常下载，1空文件占位，2不下载
     */
    public static final int VIDEO_DL_FLAG = 1;
    /**
     * 归档控制：false不归档，true归档
     */
    public static final boolean ARCHIVE_FLAG = false;

    /**
     * 下载课程类型
     */
    public static final int DL_COURSE_TYPE = CourseType.体系课.type;

    private enum CourseType{
        所有类型(0),体系课(1),企业课程(3),每日一课(4),大厂案例(5),生态课(6),公开课(8),其他(10),训练营(12);

        CourseType(int type) {
            this.type = type;
        }

        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static String getTypeName(int type){
            for (CourseType courseType : CourseType.values()) {
                if (courseType.getType() == type) {
                    return courseType.name();
                }
            }
            return "";
        }
    }

    /**
     * 下载模式
     * 0-覆盖下载，已下载课程会重新下载覆盖原文件
     * 1-增量下载，已下载课程会跳过
     */
    public static final int DL_MODEL = 1;

    private void login(String cellphone, String password) {
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
            log.info("login response:"+response);
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
        log.info("cookie:" + sb.toString());
        COOKIE = sb.toString();
    }

    /**
     * 设置较大的pageSize，一把查询完所有课程
     */
    @Override
    public void geekTime(String cellphone,String password) {
        // 如果未登录，则进行登录
        if (StringUtils.isEmpty(COOKIE)) {
            login(cellphone,password);
        }
        try {
            Course course = getCourse();
            if (course == null) {
                return;
            }
            Map<String, Object> failList = new HashMap<>();
            // 处理页面所有课程集合
            pageProcess(course, failList);
            log.info("下载完成！");
            // 进行失败列表重试
            failListProcess(failList);
            log.info("下载任务结束！");
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
        if ((fail_lesson != null&&fail_lesson.size()>0)||(fail_article != null&&fail_article.size()>0)) {
            log.info("开始进行失败任务重试...");
        }
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
        // 重试时不检查课程是否已下载、不检查当前课程类型是否配置为需要下载
        try {
            for (Long sku : fail_lesson) {
                Lesson lesson = null;
                try {
                    lesson = getArticle(sku);
                } catch (Exception e) {
                    // 只重试这一次，再失败不管了
                    log.error("    retry sku:" + sku + "课程信息获取失败，请关注");
                }
                if (lesson == null) {
                    continue;
                }
                log.info("retry****" + sku);
                // 课程内包含的所有章节集合
                List<ChapterInfo> chapterList = lesson.getList();
                // 遍历获取每个章节
                for (ChapterInfo chapterInfo : chapterList) {
                    log.info("  retry|--" + chapterInfo.getTitle());
                    // 章节内包含的所有讲集合
                    List<ArticleInfo> articleList = chapterInfo.getArticle_list();
                    // 遍历获取每一讲的内容
                    for (ArticleInfo info : articleList) {
                        Detail detail = null;
                        try {
                            detail = getDetail(info.getId());
                        } catch (Exception e) {
                            // 只重试这一次，再失败不管了
                            log.error("    retry detailId:" + info.getId() + "讲信息获取失败，请关注");
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
                            log.error("    retry" + detail.getArticle().getTitle() + "讲内容下载失败，请关注");
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
                log.error("    retry detailId:" + id + "讲信息获取失败，请关注");
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
                log.error("    retry" + detail.getArticle().getTitle() + "讲内容下载失败，请关注");
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
        // 初始化失败重试集合
        List<Long> fail_lesson = new ArrayList<>();
        List<String> fail_article = new ArrayList<>();

        // 加载已下载课程列表
        Map<String, String> existsLessons = loadLessonNameList();

        // 账号下所有课程集合
        List<CourseInfo> courseList = course.getList();
        for (CourseInfo courseInfo : courseList) {
            log.info("****" + courseInfo.getTitle());
            // 判断是否已下载，可以根据课程名跳过已下载课程
            if (DL_MODEL == 1 && checkExists(existsLessons, courseInfo.getTitle())) {
                log.error("    ["+courseInfo.getTitle()+"]课程已存在，位于 /" + existsLessons.get(courseInfo.getTitle()) + " 目录");
                continue;
            }
            // 判断课程类型的配置，决定是否下载
            if (DL_COURSE_TYPE != CourseType.所有类型.type) {
                // 不是下载所有类型的话，要具体判断
                if (courseInfo.getCourse_type() != DL_COURSE_TYPE) {
                    log.error("    ["+courseInfo.getTitle()+"] - "+CourseType.getTypeName(courseInfo.getCourse_type())+" 跳过下载。指定下载类型为：" + CourseType.getTypeName(DL_COURSE_TYPE));
                    continue;
                }
            }
            // 配置为所有类型，直接下载

            // 开始处理每一个课程
            pageProcessDownload(courseInfo,fail_lesson,fail_article);
            failList.put("fail_lesson", fail_lesson);
            failList.put("fail_article", fail_article);
            log.info("============");
        }
    }

    /**
     * 处理课程下载
     * @param courseInfo
     * @param fail_lesson
     * @param fail_article
     */
    private void pageProcessDownload(CourseInfo courseInfo, List<Long> fail_lesson,List<String> fail_article){
        // 遍历获取每一门课程
        Lesson lesson = null;
        try {
            lesson = getArticle(courseInfo.getSku());
        } catch (Exception e) {
            log.error("    sku:" + courseInfo.getSku() + "课程信息获取失败，请关注");
            //failList
            fail_lesson.add(courseInfo.getSku());
        }
        if (lesson == null) {
            return;
        }
        // 课程内包含的所有章节集合
        List<ChapterInfo> chapterList = lesson.getList();
        // 遍历获取每个章节
        for (ChapterInfo chapterInfo : chapterList) {
            log.info("  |--" + chapterInfo.getTitle());
            // 章节内包含的所有讲集合
            List<ArticleInfo> articleList = chapterInfo.getArticle_list();
            // 遍历获取每一讲的内容
            for (ArticleInfo info : articleList) {
                Detail detail = null;
                try {
                    detail = getDetail(info.getId());
                } catch (Exception e) {
                    log.error("    detailId:" + info.getId() + "讲信息获取失败，请关注");
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
                    log.error("    " + detail.getArticle().getTitle() + "讲内容下载失败，请关注");
                    e.printStackTrace();
                    //failList 某个课程的内容下载失败了，也要保存到失败列表，然后进行重试
                    fail_article.add(info.getId());
                }
            }
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
        log.info("    |--" + article.getTitle());

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
            log.info("      " + fileName + "下载完成");
            // 归档
            // 源文件路径
            Path sourcePath = Paths.get(downloadPath + fileName);
            // 目标文件路径
            Path targetPath = Paths.get(archivePath + fileName);
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                if (e instanceof FileAlreadyExistsException) {
                    log.error("      " + fileName + "归档失败：文件已存在");
                } else {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.error("      " + fileName + "下载失败：" + e.getMessage());
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
            log.info("      没有音频内容，无需下载");
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
            log.info("      没有视频内容，无需下载");
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
                log.info("      " + fileName + "下载完成");
                break;
            case 1:
                tempFile(downloadPath + fileName);
                break;
            default:
                return;
        }
        if (!ARCHIVE_FLAG){
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
                log.error("      " + fileName + "归档失败：文件已存在");
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
                log.info("      临时文件：" + filePath);
            } else {
                log.info("      临时文件已存在，不要重复创建：" + filePath);
            }
            // 关闭文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            fos.close();
        } catch (IOException e) {
            log.info("      新建临时文件失败: " + e.getMessage());
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


    @Override
    public void geekTime(long skuId) {
        List<Long> sku = new ArrayList<>();
        sku.add(skuId);

        retryFailLesson(sku);
    }


    /**
     * 加载已下载课程集合
     * @return
     */
    private Map<String, String> loadLessonNameList(){
        String path = "D:\\98-极客时间\\";
        Map<String, String> map = new HashMap<>();
        File[] subDirectories = new File(path).listFiles(File::isDirectory);
        if (subDirectories != null) {
            for (File subDirectory : subDirectories) {
                File[] firstLevelSubDirectories = subDirectory.listFiles(File::isDirectory);
                if (firstLevelSubDirectories != null) {
                    for (File firstLevelSubDirectory : firstLevelSubDirectories) {
//                        log.info(subDirectory.getName()+" - " + firstLevelSubDirectory.getName());
                        map.put(firstLevelSubDirectory.getName(), subDirectory.getName());
                    }
                }
            }
            return map;
        }
        return null;
    }

    /**
     * 检查课程是否已下载
     *  - check等于false说明课程未下载过，需下载
     *  - check等于true说明课程已下载，不要再下载
     * @param existsLessons 已下载课程集合
     * @param title 当前课程名称
     * @return
     */
    private boolean checkExists(Map<String, String> existsLessons,String title){
        if (existsLessons == null||existsLessons.size()==0) {
            return false;
        }
        return existsLessons.containsKey(title);
    }
}
