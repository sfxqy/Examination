package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Batch;
import cn.edu.hbuas.examsys.mybatis.pojo.BatchExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BatchMapper {
    int countByExample(BatchExample example);

    int deleteByExample(BatchExample example);

    int deleteByPrimaryKey(Integer bid);

    int insert(Batch record);

    int insertSelective(Batch record);

    List<Batch> selectByExample(BatchExample example);

    Batch selectByPrimaryKey(Integer bid);

    int updateByExampleSelective(@Param("record") Batch record, @Param("example") BatchExample example);

    int updateByExample(@Param("record") Batch record, @Param("example") BatchExample example);

    int updateByPrimaryKeySelective(Batch record);

    int updateByPrimaryKey(Batch record);
}