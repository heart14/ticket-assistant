package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一讲里面的视频内容
 * @author wfli
 * @since 2024/5/9 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoVideo {
    private String id;
    private String md5;
    private String url;
    private int width;
    private int height;
    private long size;
    private String time;
    private String hls_vid;
    private int version;
    private String medias;
    private String media_open;
    private int could_preview;
    private int status;
}
