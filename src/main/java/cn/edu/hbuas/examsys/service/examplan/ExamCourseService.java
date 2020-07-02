package cn.edu.hbuas.examsys.service.examplan;


import cn.edu.hbuas.examsys.dao.ArrangeDao;
import cn.edu.hbuas.examsys.dao.ExamCourseDao;
import cn.edu.hbuas.examsys.dao.StudentDao;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamcourseMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.service.baseinfo.ExamInfoService;

import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.springmvc.exception.RepositoryException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 * 考试科目相关服务：
 */
@Service
public class ExamCourseService {

    @Autowired
    private ExamCourseDao examCourseDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ArrangeDao arrangeDao;
    @Autowired
    private ExamPlanMapper examPlanMapper;



    @Autowired
    private ExamcourseMapper examcourseMapper;

    private static Logger logger = LoggerFactory.getLogger(ExamInfoService.class);

    /**
     * 二级院校批量添加考试科目信息
     * @param examcourseList  考试科目列表
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void addExanCourseList(List<Examcourse> examcourseList, HttpSession httpSession) throws Exception{
        Taskplan taskplan =(Taskplan) httpSession.getAttribute("taskplan");
        User user = (User) httpSession.getAttribute("user");
        logger.info("事务开始：二级学院添加考试科目 上传数量：{}",examcourseList.size());
        int sum = 0;

        //检查课程名称是否合法
        for (Examcourse examcourse:examcourseList){
            String courseName = examcourse.getName();
            if(courseName.contains("（")||courseName.contains("）")||courseName.contains(" "))
                throw new BusinessException("考试课程名称包含空格或中文括号："+courseName);
        }
        //创建学生表查询条件,查询学生
        for (Examcourse examcourse:examcourseList){
            Student condition = new Student();
            condition.setCid(user.getCid());
            condition.setPid(examcourse.getPid());
            condition.setClazz(examcourse.getClazz());
            condition.setGrade(examcourse.getGrade());
            condition.setState(0);
            condition.setTid(taskplan.getTid());
            List<Student> studentList = examPlanMapper.selectArrangeStudent(condition);
            int n = studentList.size();
            if(n == 0){
                throw new BusinessException("数据有误，查询不到学生数据:"+examcourse.getClazz()+"-"+examcourse.getName());
            }
            logger.info("查找学生数据成功，共查到 ：{}",n);
            //添加考试科目信息
            examcourse.setNumber(n);
            examcourse.setTotal(n);
            examcourse.setResetNumber(0);
            n = examCourseDao.addExamCourse(examcourse);
            logger.info("添加考试科目成功：{}  下一步添加考生数据",n);
            for (Student studen:studentList
            ) {
                Arrange arrange = new Arrange();
                arrange.setTid(examcourse.getTid());
                arrange.setEid(examcourse.getEid());
                arrange.setReset(0);
                arrange.setSid(studen.getSid());
                arrangeDao.addStudent(arrange);
                logger.info("添加考生信息成功，共添加：{}",studentList.size());
            }
            if(studentList.size() > 0){
                sum++;
            }
        }
        logger.info("事务结束：二级学院添加考试科目 实际数量：{}",sum);
    }

    /**
     * 二级院校添加一个考试科目信息
     * @param examcourse  考试科目列表
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void addExanCourse(Examcourse examcourse) throws Exception{
        logger.info("事务开始：二级学院添加考试科目");
        int sum = 0;
        //创建学生表查询条件,查询学生
        Student condition = new Student();
        condition.setCid(examcourse.getCid());
        condition.setPid(examcourse.getPid());
        condition.setClazz(examcourse.getClazz());
        condition.setGrade(examcourse.getGrade());
        condition.setTid(examcourse.getTid());
        condition.setState(0);

        List<Student> studentList = examPlanMapper.selectArrangeStudent(condition);
        int n = studentList.size();
        logger.info("查找学生数据成功，共查到 ：{}",n);

        //添加考试科目信息
        examcourse.setNumber(n);
        examcourse.setTotal(n);
        examcourse.setResetNumber(0);
        examcourse.setPriority(0);
        n = examCourseDao.addExamCourse(examcourse);
        logger.info("添加考试科目成功：",n);

        if(studentList.size() == 0){
            logger.info("学生数据为 0,退出...");
            return;
        }
        logger.info("添加科目信息成功，下一步级联添加考生数据...");
        for (Student studen:studentList
        ) {
            Arrange arrange = new Arrange();
            arrange.setTid(examcourse.getTid());
            arrange.setEid(examcourse.getEid());
            arrange.setReset(0);
            arrange.setSid(studen.getSid());
            arrangeDao.addStudent(arrange);
            logger.info("添加考生信息成功，共添加：{}",studentList.size());
        }
        logger.info("事务结束：二级学院添加考试科目 实际数量：{}",sum);
    }

    /**
     * 获取考试科目列表,根据搜索条件
     * TidCidPidGradeClassName
     * @param examcourse
     * @return
     */
    public List<Examcourse> selectExamListByCondition(Examcourse examcourse) throws Exception{
        List<Examcourse> examcourseList = examCourseDao.selectExamByTidCidPidGradeClassName(examcourse);
        if(examcourseList.size() == 0){
            logger.info("查询考试数据为空...");
            throw new BusinessException("查询的数据为空");
        }
        return examcourseList;
    }



    /**
     * @author sfx  此方法为修复查询数据重复时新增的方法
     * @param examcourse
     * @return
     * @throws Exception
     */
    public List<Examcourse> selectExamListBycidAndTid(Examcourse examcourse) throws Exception{
        List<Examcourse> examcourseList =examcourseMapper.getExamcourseByCidAndTid(examcourse.getCid(),examcourse.getTid());
        if(examcourseList.size() == 0){
            logger.info("查询考试数据为空...");
            throw new BusinessException("查询的数据为空");
        }
        return examcourseList;
    }


    /**
     * 分页查询所有考试科目
     * @param num 查询的页码
     * @param size 每页大小
     * @param condition 查询条件
     * @return
     * @throws Exception
     */
    public PageInfo<Examcourse> selectExamcourseByPage(Integer role,Integer num,Integer size,Examcourse condition) throws Exception{
        PageHelper.startPage(num,size);
        if(role > 0){
            condition.setCid(role);
        }
        //@author sfx  修改
        return new PageInfo<>(selectExamListBycidAndTid(condition));
       // return new PageInfo<>(selectExamListByCondition(condition));
    }

    /**
     * 根据考试任务编号，科目编号删除考试科目，同时删除考生数据，用于二级学院删除自己创建的考试科目
     * @param examcourse 学院编号 和 科目编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteExamCourse(Examcourse examcourse){
        int n = examCourseDao.deleteExamCourseByEidCid(examcourse);
        logger.info("删除考试科目：",n);
        //创建筛选条件
        Arrange condition = new Arrange();
        condition.setTid(examcourse.getTid());
        condition.setEid(examcourse.getEid());
        n = arrangeDao.deleteMoreStudent(condition);
        logger.info("删除相关考试学生：",n);
    }

    /**
     * 根据考试任务编号，科目编号删除考试科目，同时删除考生数据，用于二级学院删除自己创建的考试科目
     * @param examcourseList 学院编号 和 科目编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteExamCourseList(List<Examcourse> examcourseList,Integer cid,Integer tid){
        for (Examcourse examcourse:examcourseList){
            if(cid > 0)
                examcourse.setCid(cid);
            examcourse.setTid(tid);
            int n = examCourseDao.deleteExamCourseByEidCid(examcourse);
            logger.info("删除考试科目：{}",n);
            //创建筛选条件
            Arrange condition = new Arrange();
            condition.setTid(examcourse.getTid());
            condition.setEid(examcourse.getEid());
            n = arrangeDao.deleteMoreStudent(condition);
            logger.info("删除相关考试学生：{}",n);
        }
    }
}
