package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程包含的章节信息
 * @author wfli
 * @since 2024/5/9 21:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterInfo {
    private int id;
    private String title;
    private int count;
    private int score;
    private boolean is_last;
    private List<ArticleInfo> article_list;
}
