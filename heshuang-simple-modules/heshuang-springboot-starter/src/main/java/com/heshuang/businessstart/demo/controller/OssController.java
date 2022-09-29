package com.heshuang.businessstart.demo.controller;



import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author heshuang
 * @version v1.0
 * @CreationTime: - 2021/10/26 19:51
 * @Description:
 */
@RestController
public class OssController {

/*
    @Autowired
    private OssService ossService;


    @GetMapping("/oss")
    public RestResult uploadTest() {
        File file = new File("C:\\code\\foo-heshuang-edge\\pom.xml");
        InputStream inputStream = null;
        String url = null;
        String uploadPath = null;
        try {
            inputStream = new FileInputStream(file);
            //文件路径上传
            //uploadPath = ossService.put("test/foo-heshuang-edge/pom.xml", "C:\\code\\foo-heshuang-edge\\pom.xml", "text/xml");
            //文件流上传
            uploadPath = ossService.put("test/foo-heshuang-edge/pom.xml", inputStream, "text/xml");
            //通过objectName获取文件路径
            url = ossService.getUrl("test/foo-heshuang-edge/pom.xml");
            //通过objectName获取文件流
            inputStream = ossService.get("test/foo-heshuang-edge/pom.xml");

        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        //通过objectName移除文件
        //ossService.remove("holly/demo/pom2.xml");
        return RestResult.of("上次成功", true);
    }*/
}
