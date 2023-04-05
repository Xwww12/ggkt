package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.CourseDescription;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程简介(CourseDescription)表数据库访问层
 *
 * @author xw
 * @since 2023-04-02 18:20:19
 */
@Mapper
public interface CourseDescriptionMapper extends BaseMapper<CourseDescription> {

}

