package com.xw.ggkt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.vod.Chapter;
import com.xw.ggkt.vo.vod.ChapterVo;

import java.util.List;

/**
 * 课程(Chapter)表服务接口
 *
 * @author xw
 * @since 2023-04-03 22:33:36
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getNestedTreeList(Long courseId);

    void removeChapterByCourseId(Long id);
}

