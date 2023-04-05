package com.xw.ggkt.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xw.ggkt.mapper.SubjectMapper;
import com.xw.ggkt.model.vod.Subject;
import com.xw.ggkt.service.SubjectService;
import com.xw.ggkt.vo.vod.SubjectEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    @Resource
    private SubjectService subjectService;

    List<Subject> list = new ArrayList<>();

    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo, subject);
        subject.setId(null);
        list.add(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        subjectService.saveBatch(list);
    }
}
