package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.Teacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * 讲师(Teacher)表数据库访问层
 *
 * @author xw
 * @since 2023-03-31 15:41:46
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

}

