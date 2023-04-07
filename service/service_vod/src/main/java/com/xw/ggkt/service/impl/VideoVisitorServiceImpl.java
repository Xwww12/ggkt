package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.mapper.VideoVisitorMapper;
import com.xw.ggkt.model.vod.VideoVisitor;
import com.xw.ggkt.service.VideoVisitorService;
import com.xw.ggkt.vo.vod.VideoVisitorCountVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 视频来访者记录表(VideoVisitor)表服务实现类
 *
 * @author xw
 * @since 2023-04-05 15:10:55
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    /**
     * 查询视频在时间段内的观看人数
     * @param courseId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        //调用mapper的方法
        List<VideoVisitorCountVo> videoVisitorVoList = baseMapper.findCount(courseId,startDate,endDate);

        // 进入时间
        List<Date> dateList = videoVisitorVoList.stream()
                .map(VideoVisitorCountVo::getJoinTime)
                .collect(Collectors.toList());
        // 时间对应的观看人数
        List<Integer> countList = videoVisitorVoList.stream()
                .map(VideoVisitorCountVo::getUserCount)
                .collect(Collectors.toList());

        // 返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("xData", dateList);
        map.put("yData", countList);
        return map;
    }
}

