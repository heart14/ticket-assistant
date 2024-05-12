package com.heart.ticket.base.model.geek;

import com.heart.ticket.base.model.geek.course.CourseCount;
import com.heart.ticket.base.model.geek.course.CourseInfo;
import com.heart.ticket.base.model.geek.course.CoursePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 账号下所有课程列表
 * @author wfli
 * @since 2024/5/9 20:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private List<CourseInfo> list;

    private CoursePage page;

    private CourseCount count;
}
