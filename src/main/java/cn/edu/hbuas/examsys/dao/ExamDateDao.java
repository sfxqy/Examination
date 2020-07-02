package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.ExamdateMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Examdate;
import cn.edu.hbuas.examsys.mybatis.pojo.ExamdateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * @author SFX
 * 考试时间数据的管理
 */

@Repository
public class ExamDateDao {
    @Autowired
    private ExamdateMapper examdateMapper;

    /**
     * 多重条件查询考试时间
     * @param examdate
     * @return
     */
    public List<Examdate> selectExamTimeByCondition(Examdate examdate){
        ExamdateExample examdateExample = new ExamdateExample();
        ExamdateExample.Criteria criteria = examdateExample.createCriteria();
        if(null != examdate.getTid())
            criteria.andTidEqualTo(examdate.getTid());
        if(null != examdate.getTtid())
            criteria.andTtidEqualTo(examdate.getTtid());
        if(null != examdate.getEid())
            criteria.andEidEqualTo(examdate.getEid());
        return examdateMapper.selectByExample(examdateExample);
    }

    /**
     * 多重条件删除考试时间
     * @param examdate
     * @return
     */
    public int deleteExamTimeByCondition(Examdate examdate){
        ExamdateExample examdateExample = new ExamdateExample();
        ExamdateExample.Criteria criteria = examdateExample.createCriteria();
        if(null != examdate.getTid())
            criteria.andTidEqualTo(examdate.getTid());
        if(null != examdate.getTtid())
            criteria.andTtidEqualTo(examdate.getTtid());
        if(null != examdate.getEid())
            criteria.andEidEqualTo(examdate.getEid());
        return examdateMapper.deleteByExample(examdateExample);
    }

    /**
     * 添加一个考试时间
     * @param examdate
     * @return
     */
    public int insertExamTime(Examdate examdate){
        return examdateMapper.insertSelective(examdate);
    }

    /**
     * 批量添加考试时间
     * @param examdates
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertExamTimeList(List<Examdate> examdates){
        int total = 0;
        for (Examdate examdate:examdates){
            total += examdateMapper.insertSelective(examdate);
        }
        return total;
    }
}
