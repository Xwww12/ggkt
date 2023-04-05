package com.xw.ggkt.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.exception.SystemException;
import com.xw.ggkt.listener.SubjectListener;
import com.xw.ggkt.mapper.SubjectMapper;
import com.xw.ggkt.model.vod.Subject;
import com.xw.ggkt.service.SubjectService;
import com.xw.ggkt.vo.vod.SubjectEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程科目(Subject)表服务实现类
 *
 * @author xw
 * @since 2023-04-02 15:15:16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private SubjectListener subjectListener;

    @Override
    public List<Subject> selectList(Long id) {
        // 查询下一层课程分类
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getParentId, id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        for (Subject subject : subjectList) {
            // 判断下层是否还有节点
            Long subjectId = subject.getId();
            subject.setHasChildren(isChildren(subjectId));
        }
        return subjectList;
    }

    /**
     * 导出课程分类信息
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 设置响应类型、编码、头信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

            // 返回excel表
            List<Subject> dictList = baseMapper.selectList(null);
            ArrayList<SubjectEeVo> dictVoList = new ArrayList<>();
            for (Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类").doWrite(dictVoList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入课程分类信息
     */
    @Override
    public void importDictData(MultipartFile file) {
        if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            throw new SystemException("文件格式不支持");
        }
        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener)
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isChildren(Long id) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getParentId, id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}

