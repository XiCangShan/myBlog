package com.myall.myBlog.controller.admin;

import com.myall.myBlog.dto.JsonResult;
import com.myall.myBlog.dto.UploadFileVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.MultiPartName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
@Slf4j
@RestController
@RequestMapping("/admin/upload")
public class BackUploadController {
//    保存路径
    public final String rootPath="d:\\uploads";
//    上传允许的后缀
    public final String allowSuffix=".bmp.jpg.jpeg.png.gif.pdf.doc.zip.rar.gz";


    @RequestMapping(value = "/img",method = RequestMethod.POST)

    public JsonResult upload(@RequestParam("file") MultipartFile file){
        //获取文件名
        String fileName=file.getOriginalFilename();
//        拆分文件名和后缀
        String name=fileName.substring(0,fileName.indexOf("."));
        String suffix=fileName.substring(fileName.lastIndexOf("."));
//是否为合法后缀
        if(!allowSuffix.contains(suffix)){
           return new JsonResult().fail("上传头像文件格式错误");
        }
//获取日历对象，创建 年/月/ 文件夹
        Calendar calendar=Calendar.getInstance();
        File dirs=new File(calendar.get(Calendar.YEAR)+File.separator+(calendar.get(Calendar.MONTH)+1));
//创建目标文件
        File descFile=new File(rootPath+File.separator+dirs+File.separator+fileName);
//判断文件是否重名
        String newName=fileName;
        int i=1;
       while(descFile.exists()){
            newName=name+"("+i+")"+suffix;
            descFile=new File(rootPath+File.separator+dirs+File.separator+newName);
            i++;
       }
//       判断目录是否存在
        if(!descFile.getParentFile().exists()){
            descFile.getParentFile().mkdirs();
        }
//       保存到本地
        try {
            file.transferTo(descFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传失败，cause:{}", e);
        }
//      浏览器以 localhost:8080/uploads/2021/10/xxx.jpg 访问图片资源，需要在tomcat里设置deployment的uploads文件
        String fileUrl="/uploads/"+dirs+"/"+newName;
//        封装json
        UploadFileVO uploadFileVO=new UploadFileVO();
        uploadFileVO.setTitle(fileName);
        uploadFileVO.setSrc(fileUrl);
        return new JsonResult().ok(uploadFileVO);
    }
}
