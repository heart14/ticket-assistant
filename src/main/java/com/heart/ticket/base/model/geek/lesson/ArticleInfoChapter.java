package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一讲所属章节信息
 * @author wfli
 * @since 2024/5/9 22:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoChapter {
    private int source_id;
    private String title;
    private String sku;
    private String score;
    private String pchapter_source_id;
    private String p_chapter_title;
    private int chapter_status;
}
