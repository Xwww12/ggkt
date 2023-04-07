package com.xw.ggkt.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    //文件上传
    String upload(MultipartFile file);

    // 文件删除
    void removeFile(String fileName);
}
