package com.heart.ticket.base.model.geek.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每一讲里面的作者信息
 * @author wfli
 * @since 2024/5/9 21:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoAuthor {
    private String name;
    private String avatar;
    private String info;
    private String intro;
}
