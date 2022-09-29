package com.heshuang.miniodemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.websocket.server.PathParam;
import java.io.*;
import java.net.URL;
import java.util.UUID;
 
@RestController
@RequestMapping("/file")
public class minioTest {
 
    /*@Autowired
    private ScpClientTest scpClientTest;*/
 
    @Autowired
    private FileStorageService service;

 
    @GetMapping("/download")
    public void download(@PathParam("pathUrl") String pathUrl) throws Exception {
        byte[] bytes = service.downLoadFile(pathUrl);
 
        File file = new File("D:\\yitouxin");
        if (!file.exists()) {
            file.mkdirs();
        }
 
        File file1 = new File(file, UUID.randomUUID() + ".pdf");
 
        URL url = new URL(pathUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
 
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file1));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
 
 
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.close();
    }
 
    /**
     * 下载文件
     *
     * @param pathUrl
     * @throws IOException
     *//*
    @GetMapping("download")
    public void download(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException {
        if (filePath==null){
        }
        *//*String fileNmae = "你好啊";
        String name=new String(fileNmae.getBytes("gbk"),"utf-8");*//*
        byte[] bytes = service.downLoadFile(filePath);
        //设置相应头Content-Type  指定文件的类型
        //设置相应头Content-Disposition  指定文件以附件的形式保存
        response.setHeader("Content-Type","application/pdf");
        response.setHeader("Content-Disposition","attachment;filename="+文件名称);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
    }*/
 
   /* @GetMapping("ScpClient")
    public void ScpClient(@RequestParam("path") String path){
        scpClientTest.test(path);
    }*/
    @GetMapping("/downloadImg")
    public void downloadImg(@PathParam("pathUrl") String pathUrl) throws IOException {
        byte[] bytes = service.downLoadFile(pathUrl);
 
        File file = new File("D:\\yitouxin\\" + UUID.randomUUID() + ".jpg");
 
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.close();
    }
 
    @GetMapping("/downloadMp4")
    public void downloadMp4(@PathParam("pathUrl") String pathUrl) throws IOException {
        byte[] bytes = service.downLoadFile(pathUrl);
 
        File file = new File("D:\\yitouxin\\" + UUID.randomUUID() + ".MP4");
 
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.close();
    }
 
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public void upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        String s = service.uploadFile(file);
        System.out.println(s);
    }
 
    /**
     * 根据和文件路径删除文件
     */
    @DeleteMapping("/delete")
    public void delete(@PathParam("pathUrl") String pathUrl) {
        service.delete(pathUrl);
    }

 
 
}