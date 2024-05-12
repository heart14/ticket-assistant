package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程章节包含的每一讲
 * @author wfli
 * @since 2024/5/9 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfo {
    private String id;
    private String time;
    private String type;
    private int favorite_id;
    private int discussion_number;
    private String column_title;
    private boolean rights;
    private boolean show;
    private int rich_type;
    private long pid;
    private long sku;
    private String action;
    private long score;
    private boolean is_required;
    private String uri;
    private int column_type;
    private String enterprise_id;
    private int node_type;
    private int published;
    private int art_status;
    private int sku_status;
    private int is_sell;
    private String name;
    private String product_type;
    private int article_source;
    private int article_vendor_id;
    private ArticleInfoAuthor author;
    private ArticleInfoArticle article;
    private ArticleInfoChapter chapter;
    private ArticleInfoAudio audio;
    private ArticleInfoVideo video;
    private int anyread_total;
    private int anyread_used;
    private boolean anyread_hit;
}
