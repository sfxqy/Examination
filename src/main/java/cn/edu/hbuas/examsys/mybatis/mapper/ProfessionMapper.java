package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Profession;
import cn.edu.hbuas.examsys.mybatis.pojo.ProfessionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionMapper {
    int countByExample(ProfessionExample example);

    int deleteByExample(ProfessionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Profession record);

    int insertSelective(Profession record);

    List<Profession> selectByExample(ProfessionExample example);

    Profession selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Profession record, @Param("example") ProfessionExample example);

    int updateByExample(@Param("record") Profession record, @Param("example") ProfessionExample example);

    int updateByPrimaryKeySelective(Profession record);

    int updateByPrimaryKey(Profession record);


    //-----@author sfx-------------------------------------------------------
    int insertBatchProfession(List<Profession> professions);

    //-----@author yf--根据学院编号查专业----------------------------
    List<Profession> selectPnameByCid(Integer cid);
    //=====@author pby ----根据专业编号查询专业名称
    String selectPnameByPid(@Param("pid") Integer pid);
}