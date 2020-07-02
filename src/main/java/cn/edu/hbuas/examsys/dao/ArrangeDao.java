package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.mybatis.mapper.ArrangeMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Arrange;
import cn.edu.hbuas.examsys.mybatis.pojo.ArrangeExample;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 对考生考试计划表的操作：arrange
 */

@Repository
public class ArrangeDao {
    @Autowired
    private ArrangeMapper arrangeMapper;
    @Autowired
    private ExamPlanMapper examPlanMapper;

    /**
     * 添加一个考生考试数据
     * @param data
     */
    public int addStudent(Arrange data){
        return arrangeMapper.insertSelective(data);
    }

    /**
     * 根据考试任务编号和考试科目编号，批量删除考生考试数据
     * @param condition 删除条件：考试任务编号和考试科目编号
     */
    public int deleteMoreStudent(Arrange condition){
        ArrangeExample arrangeExample = new ArrangeExample();
        ArrangeExample.Criteria criteria = arrangeExample.createCriteria();
        criteria.andTidEqualTo(condition.getTid());
        criteria.andEidEqualTo(condition.getEid());
        return arrangeMapper.deleteByExample(arrangeExample);
    }

    /**
     * 根据条件删除考生
     * @param condition
     * @return
     */
    public int deleteStudentsByCondition(Arrange condition){
        ArrangeExample arrangeExample = new ArrangeExample();
        ArrangeExample.Criteria criteria = arrangeExample.createCriteria();
        if(condition.getEid() != null)
            criteria.andEidEqualTo(condition.getEid());
        if(condition.getTid() != null)
            criteria.andTidEqualTo(condition.getTid());
        if(condition.getReset() != null)
            criteria.andResetEqualTo(condition.getReset());
        if(condition.getSid() != null)
            criteria.andSidEqualTo(condition.getSid());
        if(condition.getAid() != null)
            criteria.andAidEqualTo(condition.getAid());
        return arrangeMapper.deleteByExample(arrangeExample);
    }

    /**
     * 根据条件查询考生
     * @param condition
     * @return
     */
    public List<Arrange> selectStudentsByCondition(Arrange condition){
        ArrangeExample arrangeExample = new ArrangeExample();
        ArrangeExample.Criteria criteria = arrangeExample.createCriteria();
        if(condition.getEid() != null)
            criteria.andEidEqualTo(condition.getEid());
        if(condition.getTid() != null)
            criteria.andTidEqualTo(condition.getTid());
        if(condition.getReset() != null)
            criteria.andResetEqualTo(condition.getReset());
        if(condition.getSid() != null)
            criteria.andSidEqualTo(condition.getSid());
        if(condition.getAid() != null)
            criteria.andAidEqualTo(condition.getAid());
        return arrangeMapper.selectByExample(arrangeExample);
    }

    /**
     * 根据考试任务编号，删除全部考生数据
     * @param tid 考试任务编号
     * @return
     */
    public int deleteStudentByTid(int tid){
        ArrangeExample arrangeExample = new ArrangeExample();
        ArrangeExample.Criteria criteria = arrangeExample.createCriteria();
        criteria.andTidEqualTo(tid);
        return arrangeMapper.deleteByExample(arrangeExample);
    }

    /**
     * 根据条件搜索考试学生数据：考试任务编号，考试科目编号
     * @param condition 搜索条件
     * @return 乱序的学生数据
     */
    public List<Arrange> selectTestStudentByCondition(Arrange condition){
        ArrangeExample arrangeExample = new ArrangeExample();
        ArrangeExample.Criteria criteria = arrangeExample.createCriteria();
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getEid())
            criteria.andEidEqualTo(condition.getEid());
        arrangeExample.setOrderByClause("RAND()");
        return arrangeMapper.selectByExample(arrangeExample);
    }

    /**
     * 根据 tid eid 查询考生详细信息,使用随机排序
     * @param tid
     * @param eid
     * @return
     * @throws Exception
     */
    public List<Examinee> selectExamineeListByTidEidByRand(Integer tid,Integer eid) throws Exception{
        if((null == tid)||(null == eid))
            throw new BusinessException("tid,eid 不能为空");
        Arrange arrange = new Arrange();
        arrange.setTid(tid);
        arrange.setEid(eid);
        return examPlanMapper.selectExamineeListByRand(arrange);
    }


    /**
     * 根据 tid eid 查询考生详细信息,查询结果顺序排序
     * @param tid
     * @param eid
     * @return
     * @throws Exception
     */
    public List<Examinee> selectExamineeListByTidEidSequence(Integer tid,Integer eid) throws Exception{
        if((null == tid)||(null == eid))
            throw new BusinessException("tid,eid 不能为空");
        Arrange arrange = new Arrange();
        arrange.setTid(tid);
        arrange.setEid(eid);
        return examPlanMapper.selectExamineeListSequence(arrange);
    }
}
