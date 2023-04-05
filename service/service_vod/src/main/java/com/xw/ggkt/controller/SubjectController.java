package com.xw.ggkt.controller;

import com.xw.ggkt.model.vod.Subject;
import com.xw.ggkt.service.SubjectService;
import com.xw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "课程分类管理")
@RestController
@RequestMapping(value="/admin/vod/subject")
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    @ApiOperation("查询下一层的课程分类")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable("id") Long id) {
        List<Subject> list = subjectService.selectList(id);
        return Result.ok(list);
    }

    @ApiOperation(value="导出")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    @ApiOperation(value = "导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        subjectService.importDictData(file);
        return Result.ok();
    }

}
