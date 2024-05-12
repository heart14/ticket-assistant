package com.heart.ticket.base.model.geek.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/5/9 20:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCount {
    private int total;
    private int end_count;
    private int unend_count;
    private int near_add_count;
    private int near_learn_count;
    private int near_expire_count;
    private int expire_end_count;
}
