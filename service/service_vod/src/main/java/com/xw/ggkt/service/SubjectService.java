package com.xw.ggkt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 课程科目(Subject)表服务接口
 *
 * @author xw
 * @since 2023-04-02 15:15:16
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> selectList(Long id);

    void exportData(HttpServletResponse response);

    void importDictData(MultipartFile file);
}

