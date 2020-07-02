package cn.edu.hbuas.examsys.service.studentinfo;

import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.StudentMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentExample;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SFX
 */

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);



    @Autowired
    private StudentMapper studentMapper;



    @Transactional(rollbackFor = Exception.class)
    public void uploadStudentInfo(Integer tid,MultipartFile file, HttpSession session) throws  Exception{
        User user=(User) session.getAttribute("user");
        List<String> propertys= Arrays.asList("sid","sname",
                "sex","grade","clazz","pid","cid","idcard","state");
        List<Student> studentList=FileUtil.readCommonalityExcel(file,0,propertys,new Teacher());
        for (Student s:studentList){
            s.setTid(tid);
            s.setPhoto("/img");
        }

        if (studentList.size()==0){
            logger.info("上传文件信息不能为空");
            throw new BusinessException("上传文件信息不能为空");
        }
        try {
            int row=studentMapper.insertBatchStudent(studentList);
            if (row!=studentList.size()){
                logger.info("插入失败，请重新上传");
                throw new BusinessException("插入失败，请重新上传");
        }
        }catch (DuplicateKeyException e){
            logger.info("插入失败，请重新上传 ：{}",e.toString());
            throw new BusinessException("插入失败，请重新上传");
        }

    }




    @Transactional(rollbackFor = Exception.class)
    public void uploadPhoto(HttpSession session,MultipartFile[] files)throws Exception{
        logger.info("service中文件组是否为空：{}，文件数量{}",files==null,files.length);
        List<Student> studentList=FileUtil.getFileName(session,files);
        for (int i=0;i<studentList.size();i++){
            StudentExample studentExample=new StudentExample();
            studentExample.createCriteria().andSidEqualTo(studentList.get(i).getSid());
            List<Student> students=studentMapper.selectByExample(studentExample);
            if (students.size()==0){
                throw new BusinessException("不存在学号为"+studentList.get(i).getSid()+"的学生的信息");
            }else {
                studentMapper.updateByExampleSelective(studentList.get(i),studentExample);
            }
        }
        FileUtil.savePhoto(session,files);
    }


    /**
     * 文件下载
     * @param response
     * @param session
     * @param cid
     * @param pid
     * @param grade
     * @param clazz
     * @throws IOException
     */
    public void download(HttpServletResponse response,HttpSession session, Integer cid, Integer pid, String grade, String clazz)throws Exception {
        List<Student> list=studentMapper.selectCid(cid, pid, grade, clazz);
        List<String> list1= new ArrayList<String>();
        for (Student student:list){
            if (student.getPhoto()!=null){
                list1.add(student.getPhoto());
            }

        }
        FileUtil.imgDownload( session,response,list1);
    }






}
