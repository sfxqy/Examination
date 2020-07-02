package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.mybatis.pojo.Arrange;
import cn.edu.hbuas.examsys.mybatis.pojo.Invigilation;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 */
@Repository
public interface ExamPlanMapper {

    /**
     * 查询要参加考试的年级列表
     * @return 年级列表数组
     */
    public List<String> selectGradeList();

    /**
     * 给指定教师增加一次监考次数
     * @param number 教师编号
     */
    public void updateTeacherOneTimes(@Param("number") String number);

    /**
     * 联合查询监考老师对应的教师信息
     * @param invigilation 查询条件：考试任务编号，院系
     * @return 老师信息列表
     */
    public List<Teacher> selectInviTeacher(Invigilation invigilation);

    /**
     * 根据条件查询详细考生数据，结果随机排序
     * @return
     */
    public List<Examinee> selectExamineeListByRand(Arrange arr);

    /**
     * 根据条件查询详细考生数据，结果顺序排序
     * @return
     */
    public List<Examinee> selectExamineeListSequence(Arrange arr);

    /**
     * 根据条件查询详细学生数据，查询结果没有排序
     * 获取考生的对应学生信息
     * @param stu 查询条件
     * @return
     */
    public List<Student> selectArrangeStudent(Student stu);
}
