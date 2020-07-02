package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import cn.edu.hbuas.examsys.mybatis.pojo.SurfaceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurfaceMapper {
    int countByExample(SurfaceExample example);

    int deleteByExample(SurfaceExample example);

    int deleteByPrimaryKey(Integer suid);

    int insert(Surface record);

    int insertSelective(Surface record);

    List<Surface> selectByExample(SurfaceExample example);

    Surface selectByPrimaryKey(Integer suid);

    int updateByExampleSelective(@Param("record") Surface record, @Param("example") SurfaceExample example);

    int updateByExample(@Param("record") Surface record, @Param("example") SurfaceExample example);

    int updateByPrimaryKeySelective(Surface record);

    int updateByPrimaryKey(Surface record);
}