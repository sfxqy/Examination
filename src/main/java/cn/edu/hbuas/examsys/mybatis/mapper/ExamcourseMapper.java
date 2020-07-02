package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.mybatis.pojo.ExamcourseExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExamcourseMapper {
    int countByExample(ExamcourseExample example);

    int deleteByExample(ExamcourseExample example);

    int deleteByPrimaryKey(Integer eid);

    int insert(Examcourse record);

    int insertSelective(Examcourse record);

    List<Examcourse> selectByExample(ExamcourseExample example);

    Examcourse selectByPrimaryKey(Integer eid);

    int updateByExampleSelective(@Param("record") Examcourse record, @Param("example") ExamcourseExample example);

    int updateByExample(@Param("record") Examcourse record, @Param("example") ExamcourseExample example);

    int updateByPrimaryKeySelective(Examcourse record);

    int updateByPrimaryKey(Examcourse record);

    /*----------------宋飞翔-----------------*/
    int insertBatchExamcourse(List<Examcourse> examcourseList);


    List<Examcourse> getExamcourseByCidAndTid(@Param("cid") Integer cid,@Param("tid") Integer tid);


}