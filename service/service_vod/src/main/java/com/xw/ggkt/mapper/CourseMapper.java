package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.Course;
import com.xw.ggkt.vo.vod.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程(Course)表数据库访问层
 *
 * @author xw
 * @since 2023-04-02 16:37:19
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(@Param("id") Long id);
}

