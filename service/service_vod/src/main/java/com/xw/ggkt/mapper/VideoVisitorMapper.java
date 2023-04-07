package com.xw.ggkt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.vod.VideoVisitor;
import com.xw.ggkt.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 视频来访者记录表(VideoVisitor)表数据库访问层
 *
 * @author xw
 * @since 2023-04-05 15:10:55
 */
@Mapper
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorCountVo> findCount(Long courseId, String startDate, String endDate);
}

