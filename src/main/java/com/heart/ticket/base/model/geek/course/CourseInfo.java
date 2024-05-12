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
public class CourseInfo {
    private long id;
    private String enterprise_id;
    private long sku;
    private String title;
    private String subtitle;
    private int course_type;
    private int column_type;
    private String product_type;
    private int total_count;
    private int total_all_count;
    private int pub_count;
    private String expire_time;
    private CourseInfoCover cover;
    private int finish_count;
    private int learn_count;
    private int learn_status;
    private int learn_percent;
    private int expire_status;
    private String last_article_id;
    private boolean is_video;
    private int status;
    private int type;
    private boolean is_show;
    private long ctime;
    private long utime;
    private CourseInfoAuthor author;
    private String total_time;
    private int total_time_sec;
    private boolean finish_status;
}
