package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherAndRole;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherExample;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper {
    int countByExample(TeacherExample example);

    int deleteByExample(TeacherExample example);

    int deleteByPrimaryKey(Integer tid);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    List<Teacher> selectByExample(TeacherExample example);

    Teacher selectByPrimaryKey(Integer tid);

    int updateByExampleSelective(@Param("record") Teacher record, @Param("example") TeacherExample example);

    int updateByExample(@Param("record") Teacher record, @Param("example") TeacherExample example);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    //  @author sfx
    int insertBatchTeacher(List<Teacher> list);

    //  @author yf
    List<TeacherAndRole> selectTeacherAndRole(Integer cid,String tname,String number);

    List<TeacherAndRole> selectSelfTeacher(@Param("cid") Integer cid);
    int deleteTeacherByCid(Integer cid);
    int deleteAllTeacher();
}