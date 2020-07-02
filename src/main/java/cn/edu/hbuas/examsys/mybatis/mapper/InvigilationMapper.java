package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Invigilation;
import cn.edu.hbuas.examsys.mybatis.pojo.InvigilationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvigilationMapper {
    int countByExample(InvigilationExample example);

    int deleteByExample(InvigilationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Invigilation record);

    int insertSelective(Invigilation record);

    List<Invigilation> selectByExample(InvigilationExample example);

    Invigilation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Invigilation record, @Param("example") InvigilationExample example);

    int updateByExample(@Param("record") Invigilation record, @Param("example") InvigilationExample example);

    int updateByPrimaryKeySelective(Invigilation record);

    int updateByPrimaryKey(Invigilation record);
}