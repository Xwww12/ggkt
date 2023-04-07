package com.xw.ggkt;

import com.xw.ggkt.service.FileService;
import com.xw.ggkt.service.impl.FileServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {
    @Resource
    private FileService fileService;

    @Test
    public void test() {
        fileService.removeFile("http://rsfubq2ix.hn-bkt.clouddn.com/test/2023-04-05 16-16-44.mp4".split("http://rsfubq2ix.hn-bkt.clouddn.com/")[1]);
    }
}
