package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一讲里面的音频内容
 * @author wfli
 * @since 2024/5/9 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoAudio {
    private String url;
    private String download_url;
    private int size;
    private String title;
    private String time;
    private String md5;
    private String dubber;
    private String id;
    private int status;
}
