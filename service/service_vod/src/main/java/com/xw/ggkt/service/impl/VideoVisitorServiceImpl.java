package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.mapper.VideoVisitorMapper;
import com.xw.ggkt.model.vod.VideoVisitor;
import com.xw.ggkt.service.VideoVisitorService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 视频来访者记录表(VideoVisitor)表服务实现类
 *
 * @author xw
 * @since 2023-04-05 15:10:55
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        return null;
    }
}

