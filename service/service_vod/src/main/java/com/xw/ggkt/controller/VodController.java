package com.xw.ggkt.controller;

import com.xw.ggkt.service.VodService;
import com.xw.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = "腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
public class VodController {
    @Resource
    private VodService vodService;

    /**
     * 视频上传
     * @return
     */
    @PostMapping("upload")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String url = vodService.uploadVideo(file);
        return Result.ok(url.replace("/", "_"));
    }

    @DeleteMapping("remove/{videoSourceId}")
    public Result removeVideo( @PathVariable String videoSourceId) {
        vodService.removeVideo(videoSourceId.replace("_", "/"));
        return Result.ok();
    }
}
