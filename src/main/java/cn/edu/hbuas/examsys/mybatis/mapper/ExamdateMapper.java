package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Examdate;
import cn.edu.hbuas.examsys.mybatis.pojo.ExamdateExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExamdateMapper {
    int countByExample(ExamdateExample example);

    int deleteByExample(ExamdateExample example);

    int deleteByPrimaryKey(Integer ttid);

    int insert(Examdate record);

    int insertSelective(Examdate record);

    List<Examdate> selectByExample(ExamdateExample example);

    Examdate selectByPrimaryKey(Integer ttid);

    int updateByExampleSelective(@Param("record") Examdate record, @Param("example") ExamdateExample example);

    int updateByExample(@Param("record") Examdate record, @Param("example") ExamdateExample example);

    int updateByPrimaryKeySelective(Examdate record);

    int updateByPrimaryKey(Examdate record);
}