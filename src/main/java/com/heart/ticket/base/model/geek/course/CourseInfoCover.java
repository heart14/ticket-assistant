package com.heart.ticket.base.model.geek.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/5/9 20:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoCover {
    private String square;
    private String rectangle;
    private String horizontal;
    private String lecture_horizontal;
    private String learn_horizontal;
    private String transparent;
    private String color;
    private String cover;
    private String rect_cover;
    private String ratio_1;
    private String ratio_4;
    private String ratio_16;
    private int cover_id;
    private int cover_status;
}
