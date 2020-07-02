package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentAndCollege;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentExample;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    int countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(StudentKey key);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(StudentKey key);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    //===@author sfx==========
    int insertBatchStudent(List<Student> list);

    List<Student> selectCid(@Param("cid") Integer cid, @Param("pid") Integer pid, @Param("grade") String grade, @Param("clazz") String clazz);

    //    author yf
    List<StudentAndCollege> selectStudentAndCollege(String sname, Integer cid);
    List<StudentAndCollege> selectSelfStudent(Integer cid);
    Student selectStudentBySid(String sid);
    int deleteStudentByCid(Integer cid);
    //删除所有学生信息
    int deleteAllStudent();
    int deleteBySid(String sid);
}