package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.ExamcourseMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.mybatis.pojo.ExamcourseExample;
import cn.edu.hbuas.examsys.service.baseinfo.ExamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 对考试科目计划表的操作：examCourse
 */

@Repository
public class ExamCourseDao {

    @Autowired
    private ExamcourseMapper examcourseMapper;


    /**
     * 添加一个考试科目
     * @param examcourse
     * @return
     */
    public int addExamCourse(Examcourse examcourse){
        return examcourseMapper.insertSelective(examcourse);
    }

    /**
     * 通过考试任务，院系，专业，年级，班级，课程名称查找考试课程
     * @param examcourse 如果某个属性字段值为空，则这个属性不参与查询
     * @return
     */
    public List<Examcourse> selectExamByTidCidPidGradeClassName(Examcourse examcourse){
        ExamcourseExample examcourseExample = new ExamcourseExample();
        ExamcourseExample.Criteria criteria = examcourseExample.createCriteria();
        if(null != examcourse.getCid())
        criteria.andCidEqualTo(examcourse.getCid());
        if(null != examcourse.getTid())
        criteria.andTidEqualTo(examcourse.getTid());
        if(null != examcourse.getPid())
        criteria.andPidEqualTo(examcourse.getPid());
        if(null != examcourse.getGrade())
        criteria.andGradeEqualTo(examcourse.getGrade());
        if(null != examcourse.getClazz())
        criteria.andClazzEqualTo(examcourse.getClazz());
        if(null != examcourse.getName())
        criteria.andNameEqualTo(examcourse.getName());
        if(null != examcourse.getTotal())
            criteria.andTotalGreaterThan(0);
        examcourseExample.setOrderByClause("cid,grade,clazz");
        return examcourseMapper.selectByExample(examcourseExample);
    }

    /**
     * 通过主键ID查询考试科目
     * @param eid
     * @return
     */
    public Examcourse selectExamById(int eid){
        return examcourseMapper.selectByPrimaryKey(eid);
    }


    /**
     * 根据考试任务编号，考试科目编号删除一个考试科目
     * @param delete
     * @return
     */

    public int deleteExamCourseByEidCid(Examcourse delete){
        ExamcourseExample examcourseExample = new ExamcourseExample();
        ExamcourseExample.Criteria criteria = examcourseExample.createCriteria();
        if(null != delete.getEid())
            criteria.andEidEqualTo(delete.getEid());
        if(null != delete.getTid())
            criteria.andTidEqualTo(delete.getTid());
        if(null != delete.getCid())
            criteria.andCidEqualTo(delete.getCid());
        return examcourseMapper.deleteByExample(examcourseExample);
    }

    /**
     * 根据考试任务编号删除考试科目计划表
     * @param tid
     * @return
     */
    public int deleteExamCourseByTid(int tid){
        ExamcourseExample examcourseExample = new ExamcourseExample();
        ExamcourseExample.Criteria criteria = examcourseExample.createCriteria();
        criteria.andTidEqualTo(tid);
        return examcourseMapper.deleteByExample(examcourseExample);
    }

    /**
     * 根据主键更新考试科目信息
     * @param examcourse
     * @return
     */
    public int updateExamCourseById(Examcourse examcourse){
        return examcourseMapper.updateByPrimaryKeySelective(examcourse);
    }
}
