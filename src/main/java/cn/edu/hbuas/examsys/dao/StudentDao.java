package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.StudentMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 牛传喜
 * 对学生表的操作
 */
@Repository
public class StudentDao {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 多重条件查找学生：如果某个条件为 NULL，则这个条件不参与查询
     * @param condition 条件：学号，姓名，身份证号，学院，年级，专业，班级，状态
     * @return
     */
    public List<Student> getStudents(Student condition){
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        if(condition.getSid() != null)
            criteria.andSidEqualTo(condition.getSid());
        if(condition.getSname() != null)
            criteria.andSnameEqualTo(condition.getSname());
        if(condition.getIdcard() != null)
            criteria.andIdcardEqualTo(condition.getIdcard());
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getPid())
            criteria.andPidEqualTo(condition.getPid());
        if(null != condition.getCid())
            criteria.andCidEqualTo(condition.getCid());
        if(null != condition.getClazz())
            criteria.andClazzEqualTo(condition.getClazz());
        if(null != condition.getGrade())
            criteria.andGradeEqualTo(condition.getGrade());
        if(null != condition.getState())
            criteria.andStateEqualTo(condition.getState());
        return studentMapper.selectByExample(studentExample);
    }
}
