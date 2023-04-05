package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.Chapter;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程(Chapter)表数据库访问层
 *
 * @author xw
 * @since 2023-04-03 22:33:35
 */
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

}

