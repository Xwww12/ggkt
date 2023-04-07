package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.controller.VodController;
import com.xw.ggkt.mapper.VideoMapper;
import com.xw.ggkt.model.vod.Video;
import com.xw.ggkt.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程视频(Video)表服务实现类
 *
 * @author xw
 * @since 2023-04-03 22:38:59
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private VodController vodController;

    // 根据课程id删除小节
    @Override
    public void removeVideoByCourseId(Long id) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId,id);
        List<Video> videoList = baseMapper.selectList(wrapper);
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodController.removeVideo(videoSourceId);
            }
        }
        baseMapper.delete(wrapper);
    }

    // 根据小节id删除小节 删除视频
    @Override
    public void removeVideoById(Long id) {
        // 删除视频后删除小节
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodController.removeVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }
}

