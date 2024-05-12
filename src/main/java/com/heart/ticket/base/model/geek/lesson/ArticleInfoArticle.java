package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一讲里面的图文内容
 * @author wfli
 * @since 2024/5/9 21:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoArticle {
    private String id;
    private String title;
    private String content;
    private String content_md;
    private long ctime;
    private String poster_wxlite;
    private int cover_hidden;
    private String subtitle;
    private String summary;
    private boolean could_preview;
    private boolean b_could_preview;
    private String content_json;
    private String content_json_short;
}
