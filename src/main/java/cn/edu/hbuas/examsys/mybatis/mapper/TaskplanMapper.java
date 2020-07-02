package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.mybatis.pojo.TaskplanExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskplanMapper {
    int countByExample(TaskplanExample example);

    int deleteByExample(TaskplanExample example);

    int deleteByPrimaryKey(Integer tid);

    int insert(Taskplan record);

    int insertSelective(Taskplan record);

    List<Taskplan> selectByExample(TaskplanExample example);

    Taskplan selectByPrimaryKey(Integer tid);

    int updateByExampleSelective(@Param("record") Taskplan record, @Param("example") TaskplanExample example);

    int updateByExample(@Param("record") Taskplan record, @Param("example") TaskplanExample example);

    int updateByPrimaryKeySelective(Taskplan record);

    int updateByPrimaryKey(Taskplan record);


    //========================@author============
    String getTitle(@Param("tid") Integer tid);
}