package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.Subject;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程科目(Subject)表数据库访问层
 *
 * @author xw
 * @since 2023-04-02 15:15:16
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

}

