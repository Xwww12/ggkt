package com.xw.ggkt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.mapper.CourseMapper;
import com.xw.ggkt.model.vod.Course;
import com.xw.ggkt.model.vod.CourseDescription;
import com.xw.ggkt.model.vod.Subject;
import com.xw.ggkt.model.vod.Teacher;
import com.xw.ggkt.service.*;
import com.xw.ggkt.vo.vod.CourseFormVo;
import com.xw.ggkt.vo.vod.CoursePublishVo;
import com.xw.ggkt.vo.vod.CourseQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程(Course)表服务实现类
 *
 * @author xw
 * @since 2023-04-02 16:37:19
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private TeacherService teacherService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private CourseDescriptionService courseDescriptionService;

    @Resource
    private VideoService videoService;

    @Resource
    private ChapterService chapterService;


    @Override
    public Map<String, Object> findPage(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        // 查询条件
        String title = courseQueryVo.getTitle();    //名称
        Long subjectId = courseQueryVo.getSubjectId();  //二级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();  //一级分类
        Long teacherId = courseQueryVo.getTeacherId();  //讲师

        // 封装查询条件
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like(Course::getTitle,title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq(Course::getSubjectId,subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq(Course::getSubjectParentId,subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq(Course::getTeacherId,teacherId);
        }

        // 查询
        Page<Course> pages = baseMapper.selectPage(coursePage, wrapper);
        long totalCount = pages.getTotal(); //总记录数
        long totalPage = pages.getPages();  //总页数
        long currentPage = pages.getCurrent();  //当前页
        long size = pages.getSize();    //每页记录数
        List<Course> records = pages.getRecords();

        // 封装讲师和分类名称
        records.stream().forEach(this::getTeacherAndSubjectName);

        // 封装返回
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);
        return map;
    }

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        // 保存基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

        // 保存简介信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);

        return course.getId();
    }

    @Override
    public CourseFormVo getCourseFormVoById(Long id) {
        // 课程基本信息
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        // 课程简介信息
        CourseDescription courseDescription = courseDescriptionService.getById(id);

        // 包装成Vo返回
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    @Override
    @Transactional
    public void updateCourseById(CourseFormVo courseFormVo) {
        // 修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        // 修改课程详细信息
        CourseDescription courseDescription = courseDescriptionService.getById(course.getId());
        if (courseDescription.getDescription() == null) {
            courseFormVo.setDescription("");
        }
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    /**
     * 发布课程
     * @param id
     * @return
     */
    @Override
    public boolean publishCourseById(Long id) {
        Course course = new Course();
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        return this.updateById(course);
    }

    //删除课程
    @Override
    public void removeCourseById(Long id) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(id);
        //根据课程id删除章节
        chapterService.removeChapterByCourseId(id);
        //根据课程id删除描述
        courseDescriptionService.removeById(id);
        //根据课程id删除课程
        baseMapper.deleteById(id);
    }


    private void getTeacherAndSubjectName(Course course) {
        // 查询讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            course.getParam().put("teacherName", teacher.getName());
        }

        // 查询当前分类和父分类名称
        Subject subject1 = subjectService.getById(course.getSubjectParentId());
        if (subject1 != null) {
            course.getParam().put("subjectParentTitle",subject1.getTitle());
        }
        Subject subject2 = subjectService.getById(course.getSubjectId());
        if (subject2 != null) {
            course.getParam().put("subjectTitle",subject2.getTitle());
        }
    }
}

