package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Disciplinary;
import cn.edu.hbuas.examsys.mybatis.pojo.DisciplinaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DisciplinaryMapper {
    int countByExample(DisciplinaryExample example);

    int deleteByExample(DisciplinaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Disciplinary record);

    int insertSelective(Disciplinary record);

    List<Disciplinary> selectByExample(DisciplinaryExample example);

    Disciplinary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Disciplinary record, @Param("example") DisciplinaryExample example);

    int updateByExample(@Param("record") Disciplinary record, @Param("example") DisciplinaryExample example);

    int updateByPrimaryKeySelective(Disciplinary record);

    int updateByPrimaryKey(Disciplinary record);
}