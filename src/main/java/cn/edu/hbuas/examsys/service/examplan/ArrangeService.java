package cn.edu.hbuas.examsys.service.examplan;

import cn.edu.hbuas.examsys.dao.ArrangeDao;
import cn.edu.hbuas.examsys.dao.ExamCourseDao;
import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Arrange;
import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.springmvc.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author SFX
 * 考生相关服务：Arrange
 */

@Service
public class ArrangeService {
    private static Logger logger = LoggerFactory.getLogger(ArrangeService.class);

    @Autowired
    private ArrangeDao arrangeDao;
    @Autowired
    private ExamCourseDao examCourseDao;
    @Autowired
    private ExamPlanMapper examPlanMapper;

    /**
     * 添加单个考生信息
     * @param arrange
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertArrangeStudent(Arrange arrange) throws Exception{
        logger.info("事务开始：添加单个考生信息");
        Student stuCondition = new Student();
        stuCondition.setTid(arrange.getTid());
        stuCondition.setSid(arrange.getSid());
        List<Student> studentList = examPlanMapper.selectArrangeStudent(stuCondition);
        if(studentList.size() <= 0)
            throw  new BusinessException("查找不到对应学生信息");
        int n = arrangeDao.addStudent(arrange);
        logger.info("添加考生信息成功...",n);

        //查找考生信息对应的科目信息
        Examcourse examcourse = examCourseDao.selectExamById(arrange.getEid());
        if(null == examcourse){
            logger.info("查找科目信息失败...");
            throw new RepositoryException("对应科目信息查找失败...","事务回滚：添加单个考生信息");
        }
        //更新对应科目的考试人数
        Examcourse update = new Examcourse();
        update.setEid(examcourse.getEid());
        update.setTotal(examcourse.getTotal()+1);
        if(arrange.getReset() == 0)
            update.setNumber(examcourse.getNumber()+1);
        if(arrange.getReset() == 1)
            update.setResetNumber(examcourse.getResetNumber()+1);
        n = examCourseDao.updateExamCourseById(update);
        logger.info("更新考试科目信息：",n);
    }


    /**
     * 批量添加考试考生
     * @param arrangeList
     * @param taskplan
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public int addRestArrList(List<Arrange> arrangeList, Taskplan taskplan,Integer eid,Integer reset) throws Exception{
        logger.info("事务开始：批量添加考生");
        int restNum = 0;
        int commNum = 0;
        int result;
        Student stuCondition = new Student();
        for (Arrange arrange:arrangeList){
            stuCondition.setTid(taskplan.getTid());
            stuCondition.setSid(arrange.getSid());
            stuCondition.setSname(arrange.getAname());
            List<Student> studentList = examPlanMapper.selectArrangeStudent(stuCondition);
            if(studentList.size() <= 0){
                throw new BusinessException("查询不到学生数据:"+arrange.getAname()+":"+arrange.getSid());
            }
            arrange.setTid(taskplan.getTid());
            arrange.setEid(eid);
            arrange.setReset(reset);
            result = arrangeDao.addStudent(arrange);
            if(result > 0){
                if(arrange.getReset() == 0){
                    commNum++;
                }else if(arrange.getReset() == 1){
                    restNum++;
                }
            }
        }
        if( (restNum+commNum) != arrangeList.size())
            throw new BusinessException("数据错误");
        //更新考试科目的人数信息
        Examcourse examcourse = examCourseDao.selectExamById(eid);
        //更新对应科目的考试人数
        Examcourse update = new Examcourse();
        update.setEid(examcourse.getEid());
        update.setTotal(examcourse.getTotal()+(restNum+commNum));
        update.setResetNumber(examcourse.getResetNumber()+restNum);
        update.setNumber(examcourse.getNumber()+commNum);
        examCourseDao.updateExamCourseById(update);
        return restNum+commNum;
    }

    /**
     * 批量删除考试考生
     * @param arrangeList
     * @param taskplan
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public int delRestArrList(List<Arrange> arrangeList, Taskplan taskplan) throws Exception{
        logger.info("事务开始：批量删除考生");
        int restNum = 0;
        int commNum = 0;
        int result;
        Arrange condition = new Arrange();
        int eid = arrangeList.get(0).getEid();
        for (Arrange arrange:arrangeList){
            condition.setTid(taskplan.getTid());
            condition.setEid(eid);
            condition.setSid(arrange.getSid());
            result = arrangeDao.deleteStudentsByCondition(condition);
            if(result > 0){
                if(arrange.getReset() ==0){
                    commNum++;
                }else if(arrange.getReset() == 1){
                    restNum++;
                }
            }
        }
        if( (restNum+commNum) != arrangeList.size())
            throw new BusinessException("数据错误");
        //更新考试科目的人数信息
        Examcourse examcourse = examCourseDao.selectExamById(eid);
        //更新对应科目的考试人数
        Examcourse update = new Examcourse();
        update.setEid(examcourse.getEid());
        update.setTotal(examcourse.getTotal()-(restNum+commNum));
        update.setResetNumber(examcourse.getResetNumber()-restNum);
        update.setNumber(examcourse.getNumber() - commNum);
        examCourseDao.updateExamCourseById(update);
        return restNum+commNum;
    }

    /**
     * 考试科目编辑中获取考试考生详细信息
     * @return
     */
    public List<Examinee> getRestArrList(int eid,int tid,int rest){
        Arrange arrange = new Arrange();
        arrange.setEid(eid);
        arrange.setTid(tid);
        arrange.setReset(rest);
        return examPlanMapper.selectExamineeListSequence(arrange);
    }
}
