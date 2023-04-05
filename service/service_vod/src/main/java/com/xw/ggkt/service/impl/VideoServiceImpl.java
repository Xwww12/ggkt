package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.mapper.VideoMapper;
import com.xw.ggkt.model.vod.Video;
import com.xw.ggkt.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * 课程视频(Video)表服务实现类
 *
 * @author xw
 * @since 2023-04-03 22:38:59
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    //根据课程id删除小节
    @Override
    public void removeVideoByCourseId(Long id) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,id);
        baseMapper.delete(wrapper);
    }
}

