package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程视频(Video)表数据库访问层
 *
 * @author xw
 * @since 2023-04-03 22:38:59
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}

