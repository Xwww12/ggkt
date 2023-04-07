package com.xw.ggkt.service.impl;

import com.xw.ggkt.config.ConstantPropertiesUtil;
import com.xw.ggkt.service.FileService;
import com.xw.ggkt.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class VodServiceImpl implements VodService {

    @Resource
    private FileService fileService;

    @Override
    public String uploadVideo(MultipartFile file) {
        String url = fileService.upload(file);
        return url;
    }

    @Override
    public void removeVideo(String videoSourceId) {
        if (!videoSourceId.startsWith(ConstantPropertiesUtil.DOMAIN_NAME)) {
            fileService.removeFile(videoSourceId);
        } else {
            fileService.removeFile(videoSourceId.split(ConstantPropertiesUtil.DOMAIN_NAME)[1]);
        }
    }
}
