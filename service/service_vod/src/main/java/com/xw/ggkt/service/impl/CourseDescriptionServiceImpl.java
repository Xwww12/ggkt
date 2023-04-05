package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.mapper.CourseDescriptionMapper;
import com.xw.ggkt.model.vod.CourseDescription;
import com.xw.ggkt.service.CourseDescriptionService;
import org.springframework.stereotype.Service;

/**
 * 课程简介(CourseDescription)表服务实现类
 *
 * @author xw
 * @since 2023-04-02 18:20:19
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}

