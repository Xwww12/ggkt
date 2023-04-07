package com.xw.ggkt.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 点播
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String videoSourceId);
}
