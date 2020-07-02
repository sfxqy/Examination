package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.TeacherMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 对教师表的操作
 */
@Repository
public class TeacherDao {
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 根据条件查找教师信息
     * @param condition
     * @return
     */
    public List<Teacher> selectTeacherByCondition(Teacher condition){
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria criteria = teacherExample.createCriteria();
        if(null != condition.getNumber())
            criteria.andNumberEqualTo(condition.getNumber());
        if(null != condition.getCid())
            criteria.andCidEqualTo(condition.getCid());
        if(null != condition.getPhone())
            criteria.andPhoneEqualTo(condition.getPhone());
        if(null != condition.getSex())
            criteria.andSexEqualTo(condition.getSex());
        if(null != condition.getRid())
            criteria.andRidEqualTo(condition.getRid());
        return teacherMapper.selectByExample(teacherExample);
    }

    /**
     * 根据教师工号查找教师
     * @param number 教师工号
     * @return 查不到返回NULL，查到返回教师数据
     */
    public Teacher selectTeacherByNumber(String number){
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria criteria = teacherExample.createCriteria();
        if(number != null)
            criteria.andNumberEqualTo(number);
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        if(teacherList.size() <= 0)
            return null;
        else
            return teacherList.get(0);
    }
}
