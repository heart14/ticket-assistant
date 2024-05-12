package com.heart.ticket.base.model.geek;

import com.heart.ticket.base.model.geek.lesson.ChapterInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 具体每一个课程信息
 * @author wfli
 * @since 2024/5/9 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    private List<ChapterInfo> list;

    private boolean has_chapter;

    private boolean is_show;

    private int anyread_total;

    private int anyread_used;
}
