package com.xw.ggkt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.vod.Video;

/**
 * 课程视频(Video)表服务接口
 *
 * @author xw
 * @since 2023-04-03 22:38:59
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(Long id);

    void removeVideoById(Long id);
}

