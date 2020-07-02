package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Native;
import cn.edu.hbuas.examsys.mybatis.pojo.NativeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NativeMapper {
    int countByExample(NativeExample example);

    int deleteByExample(NativeExample example);

    int deleteByPrimaryKey(String card);

    int insert(Native record);

    int insertSelective(Native record);

    List<Native> selectByExample(NativeExample example);

    Native selectByPrimaryKey(String card);

    int updateByExampleSelective(@Param("record") Native record, @Param("example") NativeExample example);

    int updateByExample(@Param("record") Native record, @Param("example") NativeExample example);

    int updateByPrimaryKeySelective(Native record);

    int updateByPrimaryKey(Native record);
}