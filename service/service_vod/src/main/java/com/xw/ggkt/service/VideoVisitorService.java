package com.xw.ggkt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.vod.VideoVisitor;

import java.util.Map;

/**
 * 视频来访者记录表(VideoVisitor)表服务接口
 *
 * @author xw
 * @since 2023-04-05 15:10:55
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}

