package cn.edu.hbuas.examsys.service.readExcel;

import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.*;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.service.examplan.ArrangeService;
import cn.edu.hbuas.examsys.service.examplan.ExamCourseService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.FileUtil;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author SFX
 */
@Service
public class ReadExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ReadExcelService.class);

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ExamcourseMapper examcourseMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private ProfessionMapper professionMapper;
    @Autowired
    private ExamCourseService examCourseService;

    @Autowired
    private ArrangeMapper arrangeMapper;


    @Autowired
    private ArrangeService arrangeService;


    /**
     * 读取教师excel表
     * @param session
     * @param file excel
     * @throws Exception
     */
   @Transactional(rollbackFor = Exception.class)
   public void readTeacher(HttpSession session, MultipartFile file)throws Exception{
       User user=(User)session.getAttribute("user");
       List<String> propertys= Arrays.asList("number","tname",
               "sex","wages","phone","cid","rid");
       List<Teacher> teacherList=FileUtil.readCommonalityExcel(file,user.getCid(),propertys,new Teacher());
       for(Teacher t:teacherList){
           logger.info("{}--{}--{}--{}--{}--{}--{}",t.getNumber(),t.getTname(),
           t.getSex(),t.getWages(),t.getPhone(),t.getCid(),t.getRid());
       }
       if (teacherList.size()==0){
           logger.info("上传文件信息不能为空");
           throw new BusinessException("上传文件信息不能为空");
       }
       try {
           int row=teacherMapper.insertBatchTeacher(teacherList);
           if (row!=teacherList.size()){
               logger.info("插入失败，请重新上传");
               throw new BusinessException("插入失败，请重新上传");
          }
       }catch (DuplicateKeyException e){
           logger.info("插入失败，存在相同工号教师数据 ：{}",e.toString());
           throw new BusinessException("插入失败，存在相同工号教师数据");
       }catch (Exception e){
           throw new BusinessException("上传文件不合法，请修改后上传");
       }

   }


    /**
     * 读取考试科目安排表
     * @param session
     * @param file excel
     * @param tid 考试任务编号
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void readExamcourse(HttpSession session, MultipartFile file,Integer tid)throws Exception{
        User user=(User)session.getAttribute("user");
        List<String> propertys= Arrays.asList("cid","pid",
                "grade","clazz","name",/*"number","resetNumber","total",*/"testForm","priority");
        List<Examcourse> examcourseList=FileUtil.readCommonalityExcel(file,user.getCid(),propertys,new Examcourse());
        for(Examcourse e:examcourseList){
            e.setTid(tid);
            e.setAccomplish(0);
            e.setResetNumber(0);
            e.setNumber(0);
        }
        if (examcourseList.size()==0){
            logger.info("上传文件信息不能为空");
            throw new BusinessException("上传文件信息不能为空");
        }
        try {
            examCourseService.addExanCourseList(examcourseList,session);
        }catch (DuplicateKeyException e){
            logger.info("插入失败，请重新上传 ：{}",e.toString());
            throw new BusinessException("插入失败，请重新上传");
        }catch (BusinessException e){
            e.printStackTrace();
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("未知错误");
        }
    }


    /**
     * 读取考场Excel表
     * @param session
     * @param file excel
     * @param tid 考试任务编号
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void readRoom(HttpSession session, MultipartFile file,Integer tid)throws Exception{
        User user=(User)session.getAttribute("user");
        List<String> propertys= Arrays.asList("rid","place","number",
                "campus","storid");
        List<Room> roomList=FileUtil.readCommonalityExcel(file,0,propertys,new Room());

        RoomExample roomExample=new RoomExample();
        roomExample.createCriteria().andTidEqualTo(tid);
        List<Room> rooms=roomMapper.selectByExample(roomExample);
        if (rooms.size()!=0&&roomList.retainAll(rooms)){
            throw new BusinessException("导入的数据有部分（或全部）已存在数据库中");
        }

        for(Room r:roomList){
            r.setTid(tid);
        }
        if (roomList.size()==0){
            logger.info("上传文件信息不能为空");
            throw new BusinessException("上传文件信息不能为空");
        }
        try {
            int row=roomMapper.insertBatchRoom(roomList);
            if (row!=roomList.size()){
                logger.info("插入失败，请重新上传");
                throw new BusinessException("插入失败，请重新上传");
            }
        }catch (DuplicateKeyException e){
            logger.info("插入失败，请重新上传 ：{}",e.toString());
            throw new BusinessException("插入失败，请重新上传");
        }catch (Exception e){
            throw new BusinessException("上传文件不合法，请修改后上传");
        }
    }


    /**
     * 上传院别专业表
     * @param session
     * @param file
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void readProfession(HttpSession session, MultipartFile file)throws Exception{
        User user=(User)session.getAttribute("user");
        List<String> propertys= Arrays.asList("cid","pid","pname");
        List<Profession> professionList=FileUtil.readCommonalityExcel(file,user.getCid(),propertys,new Profession());

        if (professionList.size()==0){
            logger.info("上传文件信息不能为空");
            throw new BusinessException("上传文件信息不能为空");
        }
        try {
            int row=professionMapper.insertBatchProfession(professionList);
            if (row!=professionList.size()){
                logger.info("插入失败，请重新上传");
                throw new BusinessException("插入失败，请重新上传");
            }
        }catch (DuplicateKeyException e){
            logger.info("插入失败，请重新上传 ：{}",e.toString());
            throw new BusinessException("插入失败，请重新上传");
        }catch (Exception e){
            throw new BusinessException("上传文件不合法，请修改后上传");
        }
    }





    @Transactional(rollbackFor = Exception.class)
    public void readReset(HttpSession session, MultipartFile file,Integer tid,Taskplan taskplan)throws Exception{
        List<String> propertys= Arrays.asList("sid","aname");
        List<Arrange> arrangeList=FileUtil.readCommonalityExcel(file,0,propertys,new Arrange());
        ExamcourseExample examcourseExample=new ExamcourseExample();
        examcourseExample.createCriteria().andTidEqualTo(tid);
        List<Examcourse> examcourseList=examcourseMapper.selectByExample(examcourseExample);
        for (Arrange a:arrangeList){
            Integer eid=null;
            a.setTid(tid);
            for (int i=0;i<examcourseList.size();i++){
                if (examcourseList.get(i).getName().equals(a.getAname())){
                    a.setEid(examcourseList.get(i).getEid());
                    eid=examcourseList.get(i).getEid();
                    break;
                }
                if (i==(examcourseList.size()-1)){
                    logger.info("学号为：{}的学生的重修科目有误，请修改后上传",a.getSid());
                    throw new BusinessException("学号为："+a.getSid()+"的学生的重修科目有误，请修改后上传");
                }
            }
            a.setReset(1);
            List<Arrange> arranges=new ArrayList<>();
            arranges.add(a);
            arrangeService.addRestArrList(arranges,taskplan,eid,1);
        }
        if (arrangeList.size()==0){
            logger.info("上传文件信息不能为空");
            throw new BusinessException("上传文件信息不能为空");
        }
        try {
            int row=arrangeMapper.insertBatchArrange(arrangeList);
            if (row!=arrangeList.size()){
                logger.info("插入失败，请重新上传");
                throw new BusinessException("插入失败，请重新上传");
            }
        }catch (DuplicateKeyException e){
            logger.info("插入失败，请重新上传 ：{}",e.toString());
            throw new BusinessException("插入失败，请重新上传");
        }catch (Exception e){
            throw new BusinessException("上传文件不合法，请修改后上传");
        }
    }
}
