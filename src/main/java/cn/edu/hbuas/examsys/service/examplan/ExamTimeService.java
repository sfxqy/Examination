package cn.edu.hbuas.examsys.service.examplan;

import cn.edu.hbuas.examsys.dao.ExamDateDao;
import cn.edu.hbuas.examsys.mybatis.pojo.Examdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SFX
 * 考试时间服务
 */
@Service
public class ExamTimeService {
    @Autowired
    private ExamDateDao examDateDao;

    /**
     * 添加一个考试时间
     * @param examdate
     * @return
     */
    public int insertExamDate(Examdate examdate){
        return examDateDao.insertExamTime(examdate);
    }

    /**
     * 查询全部的考试时间
     * @param tid
     * @return
     */
    public List<Examdate> selectAllExamTimes(Integer tid){
        Examdate examdate = new Examdate();
        examdate.setTid(tid);
        return examDateDao.selectExamTimeByCondition(examdate);
    }

    /**
     * 根据考试任务删除全部的考试时间
     * @param tid
     * @return
     */
    public int deleteAllExamTimes(Integer tid){
        Examdate examdate = new Examdate();
        examdate.setTid(tid);
        return examDateDao.deleteExamTimeByCondition(examdate);
    }

    /**
     * 批量添加考试时间
     * @param examdates
     * @return
     */
    public int insertExamTimeList(List<Examdate> examdates,Integer tid){
        for (Examdate examdate:examdates){
            examdate.setTid(tid);
        }
        return examDateDao.insertExamTimeList(examdates);
    }
}
