package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.mapper.ChapterMapper;
import com.xw.ggkt.model.vod.Chapter;
import com.xw.ggkt.model.vod.Video;
import com.xw.ggkt.service.ChapterService;
import com.xw.ggkt.service.VideoService;
import com.xw.ggkt.vo.vod.ChapterVo;
import com.xw.ggkt.vo.vod.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程(Chapter)表服务实现类
 *
 * @author xw
 * @since 2023-04-03 22:33:36
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    private VideoService videoService;

    /**
     * 章节小节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getNestedTreeList(Long courseId) {
        // 根据课程id查询章节
        LambdaQueryWrapper<Chapter> chapterWrapper = new LambdaQueryWrapper<>();
        chapterWrapper.eq(Chapter::getCourseId, courseId)
                .orderByAsc(Chapter::getSort, Chapter::getId);
        List<Chapter> chapterList = baseMapper.selectList(chapterWrapper);

        // 根据课程id查询视频
        LambdaQueryWrapper<Video> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(Video::getCourseId, courseId)
                .orderByAsc(Video::getSort, Video::getId);
        List<Video> videoList = videoService.list(videoWrapper);

        // 封装为ChapterVo返回
        ArrayList<ChapterVo> chapterVoList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            List<VideoVo> videoVoList = videoList.stream()
                .filter(video -> chapter.getId().equals(video.getChapterId()))
                .map(video -> {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    return videoVo;
                }).collect(Collectors.toList());

            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }

        return chapterVoList;
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(Long id) {
        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getCourseId,id);
        baseMapper.delete(wrapper);
    }
}

