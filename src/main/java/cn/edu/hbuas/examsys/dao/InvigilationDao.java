package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.InvigilationMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Invigilation;
import cn.edu.hbuas.examsys.mybatis.pojo.InvigilationExample;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 对监考老师计划表的操作：invigilation
 */

@Repository
public class InvigilationDao {
    @Autowired
    private InvigilationMapper invigilationMapper;
    @Autowired
    private ExamPlanMapper examPlanMapper;

    /**
     * 添加一个监考老师
     * @param newInv
     * @return
     */
    public int insertInvi(Invigilation newInv){
        return invigilationMapper.insertSelective(newInv);
    }


    /**
     * 根据考试任务编号删除监考老师数据
     * @param tid
     * @return
     */
    public int deleteInvigilationByTid(int tid){
        InvigilationExample invigilationExample = new InvigilationExample();
        InvigilationExample.Criteria criteria = invigilationExample.createCriteria();
        criteria.andTidEqualTo(tid);
        return invigilationMapper.deleteByExample(invigilationExample);
    }

    /**
     * 获取监考老师对应的详细教师信息
     * @param invigilation 查询条件：考试任务编号，院别编号
     * @return 查询的教师信息
     */
    public List<Teacher> selectInviTeacherInfo(Invigilation invigilation){
        return examPlanMapper.selectInviTeacher(invigilation);
    }

    /**
     * 根据主键批量删除监考老师
     * @param invigilationList
     * @return
     */
    public int deleteInviList(List<Invigilation> invigilationList){
        for (Invigilation invigilation:invigilationList){
            invigilationMapper.deleteByPrimaryKey(invigilation.getId());
        }
        return invigilationList.size();
    }

    /**
     * 条件删除所有的监考老师
     * @param invigilation 查询条件
     * @return
     */
    public int deleteInviByCondition(Invigilation invigilation){
        InvigilationExample invigilationExample = new InvigilationExample();
        InvigilationExample.Criteria criteria = invigilationExample.createCriteria();
        if(null != invigilation.getId())
            criteria.andIdEqualTo(invigilation.getId());
        if(null != invigilation.getTid())
            criteria.andTidEqualTo(invigilation.getTid());
        if(null != invigilation.getCid())
            criteria.andCidEqualTo(invigilation.getCid());
        if(null != invigilation.getNumber())
            criteria.andNumberEqualTo(invigilation.getNumber());
        return invigilationMapper.deleteByExample(invigilationExample);
    }

    /**
     * 根据各种条件查询监考老师，如果某个条件为NULL，则查找时不包括这个条件
     * @param condition
     * @return
     */
    public List<Invigilation> selectInviByCondition(Invigilation condition){
        InvigilationExample invigilationExample = new InvigilationExample();
        InvigilationExample.Criteria criteria = invigilationExample.createCriteria();
        if(null != condition.getCid())
            criteria.andCidEqualTo(condition.getCid());
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getNumber())
            criteria.andNumberEqualTo(condition.getNumber());
        if(null != condition.getId())
            criteria.andIdEqualTo(condition.getId());
        invigilationExample.setOrderByClause("cid");
        return invigilationMapper.selectByExample(invigilationExample);
    }

}
