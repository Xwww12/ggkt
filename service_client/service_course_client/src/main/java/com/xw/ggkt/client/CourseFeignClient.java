package com.xw.ggkt.client;

import com.xw.ggkt.model.vod.Course;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("service-vod")
public interface CourseFeignClient {
    @ApiOperation("根据关键字查询课程")
    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    List<Course> findByKeyword(@PathVariable String keyword);
}
