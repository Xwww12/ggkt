package com.xw.ggkt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xw.ggkt.model.vod.Course;
import com.xw.ggkt.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/vod/course")
public class CourseApiController {

    @Resource
    private CourseService courseService;

    /**
     * 用于远程调用
     * @param keyword
     * @return
     */
    @ApiOperation("根据关键字查询课程")
    @GetMapping("inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(
            @ApiParam(value = "关键字", required = true)
            @PathVariable String keyword){
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Course::getTitle, keyword);
        List<Course> list = courseService.list(queryWrapper);
        return list;
    }
}
