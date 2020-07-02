package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Arrange;
import cn.edu.hbuas.examsys.mybatis.pojo.ArrangeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrangeMapper {
    int countByExample(ArrangeExample example);

    int deleteByExample(ArrangeExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(Arrange record);

    int insertSelective(Arrange record);

    List<Arrange> selectByExample(ArrangeExample example);

    Arrange selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("record") Arrange record, @Param("example") ArrangeExample example);

    int updateByExample(@Param("record") Arrange record, @Param("example") ArrangeExample example);

    int updateByPrimaryKeySelective(Arrange record);

    int updateByPrimaryKey(Arrange record);



    /*=================@author======*/
    int insertBatchArrange(List<Arrange> arranges);
}