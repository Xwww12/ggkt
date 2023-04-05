package com.xw.ggkt.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xw.exception.SystemException;
import com.xw.ggkt.config.ConstantPropertiesUtil;
import com.xw.ggkt.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    String accessKey = ConstantPropertiesUtil.ACCESS_KEY_ID;

    String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

    String bucket = ConstantPropertiesUtil.BUCKET_NAME;

    @Override
    public String upload(MultipartFile file) {
        // 判断文件类型
        if (file == null) {
            throw new SystemException("文件为空");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null ||
                (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg"))) {
            throw new SystemException("文件格式不正确");
        }


        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = generateFileName(file);
        try {
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                // System.out.println(putRet.key);
                // System.out.println(putRet.hash);
                return "rsfubq2ix.hn-bkt.clouddn.com/" + key;    // 返回图片地址
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException ex) {
            //ignore
        }
        return null;
    }

    private String generateFileName(MultipartFile file) {
        String name = UUID.randomUUID().toString().replaceAll("-","")+
                file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        return datePath + name;
    }
}
