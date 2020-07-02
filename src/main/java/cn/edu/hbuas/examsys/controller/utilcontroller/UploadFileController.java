package cn.edu.hbuas.examsys.controller.utilcontroller;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.service.baseinfo.Student1Service;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author SFX
 * 向服务器上传文件
 */
@Api(tags = "向服务器上传文件")
@RestController
@RequestMapping("/uploadfile")
public class UploadFileController {
    @Autowired
    private Student1Service student1Service;

    private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @ResponseBody
    @PostMapping("/img")
    @ApiOperation("上传单个学生文件")
    public ResponseData uploadSingleImage(MultipartFile multipartFile, HttpSession httpSession) throws Exception{
        if(multipartFile == null || multipartFile.isEmpty())
            throw new BusinessException("文件上传失败");
        User user = (User)httpSession.getAttribute("user");
        String originName = multipartFile.getOriginalFilename();
        String idcard;
        String endStr;
        //String path = "/var/kwxt/StuImg";
        String str=File.separator+"photo"+File.separator;
        ServletContext servletContext = httpSession.getServletContext();
        String path = servletContext.getRealPath(str);
       // String path = "D://data";
        System.out.println(path);
        File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdir();
        }
        if(originName.endsWith(".png")||originName.endsWith(".jpg")||originName.endsWith(".bmp")){
            String strings[] = originName.split("\\.");
            idcard = strings[0];
            endStr = "."+strings[1];
          //  System.out.println("省份正："+idcard);
        }else {
            throw new BusinessException("文件类型错误");
        }
        if(multipartFile.getSize() > 1024*1024){
            throw new BusinessException("文件大于200KB");
        }
        Student condition = new Student();
        condition.setIdcard(idcard);
        Student student = student1Service.getStudentByIdcard(condition);
        if(student == null){
            throw new BusinessException("学生信息不存在");
        }
        if(student.getCid() != user.getCid()){
            if(user.getCid() != -1){
                throw new BusinessException("请上传本院学生数据");
            }
        }
        File file = new File(path+"/"+student.getSid()+endStr);
        if(file.exists()){
            throw new BusinessException("学生照片已经存在:"+student.getSname());
        }
        file = new File(path+"/"+student.getSid()+endStr);
        multipartFile.transferTo(file);
        return new ResponseData("照片上传成功",true);
    }

    @ResponseBody
    @ApiOperation("上传学生照片的压缩文件")
    @PostMapping("/zipimgs")
    public ResponseData uploadZipImgs(MultipartFile multipartFile,HttpSession httpSession) throws Exception{
        if(multipartFile == null || multipartFile.isEmpty())
            throw new BusinessException("文件上传失败");
        User user = (User) httpSession.getAttribute("user");
        if(user.getCid() > 0)
            throw new BusinessException("用户身份信息错误");
        String str=File.separator+"photo"+File.separator;
        ServletContext servletContext = httpSession.getServletContext();
        String path = servletContext.getRealPath(str);
        File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdir();
        }
        String filename = multipartFile.getOriginalFilename();
        if(!filename.endsWith(".zip")){
            throw new BusinessException("请上传 ZIP 格式压缩文件");
        }
        File originalFile = new File(path+"/"+filename);
        if(originalFile.exists()){
            originalFile.setWritable(true);
            originalFile.delete();
        }
        multipartFile.transferTo(originalFile);
        ZipFile zipFile = new ZipFile(originalFile);
        ZipEntry zipEntry;
        String pathName;
        Enumeration zipEntryEnum = zipFile.entries();
        while (zipEntryEnum.hasMoreElements()){
            zipEntry = (ZipEntry) zipEntryEnum.nextElement();
            if(!zipEntry.isDirectory()){
                pathName = zipEntry.getName();
                if(pathName.endsWith(".png")||pathName.endsWith(".jpg")||pathName.endsWith(".bmp"))
                {
                    if(zipEntry.getSize() < (1024*1024)){
                        saveZipEntryFile(zipFile.getInputStream(zipEntry),pathName,path);
                    }else {
                        logger.info("文件大小超出限制：{}",pathName);
                    }
                }
            }
        }
        zipFile.close();
        originalFile.delete();
        return new ResponseData("文件上传成功",true);
    }

    private void saveZipEntryFile(InputStream inputStream,String orignalName,String toPath) throws Exception{
        String idCard = orignalName.substring(orignalName.lastIndexOf("/")+1,orignalName.lastIndexOf("."));
        String endStr = orignalName.split("\\.")[1];
        Student condition = new Student();
        condition.setIdcard(idCard);
        Student student = student1Service.getStudentByIdcard(condition);
        if(student == null){
            logger.info("没有此身份证号的考生:{}",idCard);
            return;
        }
        File file = new File(toPath+"/"+student.getSid()+"."+endStr);
        if(file.exists()){
            file.setWritable(true);
            file.delete();
        }
        FileOutputStream outputStream = new FileOutputStream(toPath+"/"+student.getSid()+"."+endStr);
        byte[] buffer = new byte[1024];
        int readLen;
        while ((readLen = inputStream.read(buffer,0,1024)) > 0){
            outputStream.write(buffer,0,readLen);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }
}
