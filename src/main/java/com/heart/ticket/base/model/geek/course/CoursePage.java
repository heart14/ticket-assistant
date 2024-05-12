package com.heart.ticket.base.model.geek.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2024/5/9 20:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePage {
    private int page;
    private int size;
    private boolean more;
    private int total_count;
    private int total_page;
}
