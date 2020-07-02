package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.TaskplanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.ExamcourseExample;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.mybatis.pojo.TaskplanExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 考试任务计划表：TaskPlan
 */
@Repository
public class TaskPlanDao {
    @Autowired
    private TaskplanMapper taskplanMapper;
    @Autowired
    private ExamPlanMapper examPlanMapper;

    /**
     * 查询所有的考试计划信息
     * @return 考试信息数组
     * @throws Exception
     */
    public List<Taskplan> getAllExamData(){
        TaskplanExample taskplanExample = new TaskplanExample();
        TaskplanExample.Criteria criteria = taskplanExample.createCriteria();
        return taskplanMapper.selectByExample(taskplanExample);
    }

    /**
     * 根据主键更改考试任务信息
     * @param taskplan
     * @return
     */
    public int updateTaskPlanById(Taskplan taskplan){
        return taskplanMapper.updateByPrimaryKey(taskplan);
    }

    /**
     * 根据开始，结束时间，任务描述，任务状态查找考试任务
     * @param select
     * @return
     */
    public List<Taskplan> selectTaskPlanByCondition(Taskplan select){
        TaskplanExample taskplanExample = new TaskplanExample();
        TaskplanExample.Criteria criteria = taskplanExample.createCriteria();
        if(null != select.getBegin())
        criteria.andBeginEqualTo(select.getBegin());
        if(null != select.getEnd())
        criteria.andEndEqualTo(select.getEnd());
        if(null != select.getDescription())
        criteria.andDescriptionEqualTo(select.getDescription());
        if(null != select.getState())
        criteria.andStateEqualTo(select.getState());
        return taskplanMapper.selectByExample(taskplanExample);
    }

    /**
     * 根据主键查询考试任务
     * @param tid 主键
     * @return 如果没有查询到返回空
     */
    public Taskplan selectTaskPlanByID(int tid){
        return taskplanMapper.selectByPrimaryKey(tid);
    }

   /**
     * 添加一个考试任务
     * @param taskplan
     */
    public int addTask(Taskplan taskplan){
        return taskplanMapper.insertSelective(taskplan);
    }

    /**
     * 删除一个考试任务
     * @param delete
     * @return 返回影响行数
     */
    public int deleteTask(Taskplan delete){
        return taskplanMapper.deleteByPrimaryKey(delete.getTid());
    }


    /**
     * 查找所有要参加考试的年级列表
     * @return 字符串数组，存放年级列表
     */
    public List<String> selectTestGradeList(){
        return examPlanMapper.selectGradeList();
    }

    /**
     * 根据状态获取对应的考试任务
     * @param min
     * @param max
     * @return
     */
    public List<Taskplan> selectTaskByState(String min,String max){
        TaskplanExample taskplanExample = new TaskplanExample();
        TaskplanExample.Criteria criteria = taskplanExample.createCriteria();
        criteria.andStateBetween(min,max);
        return taskplanMapper.selectByExample(taskplanExample);
    }
}
