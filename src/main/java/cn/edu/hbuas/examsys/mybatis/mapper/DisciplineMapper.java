package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.*;

import java.util.List;

import cn.edu.hbuas.examsys.mybatis.pojo.AgainstTest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineMapper {
    int countByExample(DisciplineExample example);

    int deleteByExample(DisciplineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Discipline record);

    int insertSelective(Discipline record);

    List<Discipline> selectByExample(DisciplineExample example);

    Discipline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Discipline record, @Param("example") DisciplineExample example);

    int updateByExample(@Param("record") Discipline record, @Param("example") DisciplineExample example);

    int updateByPrimaryKeySelective(Discipline record);

    int updateByPrimaryKey(Discipline record);

    /*author:yf 违纪信息查询*/
   List<AgainstTest> selectAgainstTestStudents();

    /*author:yf 缺考信息查询*/
    List<AgainstTest> selectMissTestStudents();

    /*author:yf 添加缺考信息前根据学号查询相关信息*/
    List<AgainstTest1> selectStudentInfo(String sid);
}