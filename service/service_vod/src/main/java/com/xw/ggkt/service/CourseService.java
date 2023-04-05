package com.xw.ggkt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.vod.Course;
import com.xw.ggkt.vo.vod.CourseFormVo;
import com.xw.ggkt.vo.vod.CoursePublishVo;
import com.xw.ggkt.vo.vod.CourseQueryVo;

import java.util.Map;

/**
 * 课程(Course)表服务接口
 *
 * @author xw
 * @since 2023-04-02 16:37:19
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findPage(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseFormVoById(Long id);

    void updateCourseById(CourseFormVo courseFormVo);

    CoursePublishVo getCoursePublishVo(Long id);

    boolean publishCourseById(Long id);

    void removeCourseById(Long id);
}

