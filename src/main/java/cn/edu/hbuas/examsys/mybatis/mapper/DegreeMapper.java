package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.AdmissionCard;
import cn.edu.hbuas.examsys.mybatis.pojo.Degree;
import cn.edu.hbuas.examsys.mybatis.pojo.DegreeExample;
import cn.edu.hbuas.examsys.mybatis.pojo.DegreeKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeMapper {
    int countByExample(DegreeExample example);

    int deleteByExample(DegreeExample example);

    int deleteByPrimaryKey(DegreeKey key);

    int insert(Degree record);

    int insertSelective(Degree record);

    List<Degree> selectByExample(DegreeExample example);

    Degree selectByPrimaryKey(DegreeKey key);

    int updateByExampleSelective(@Param("record") Degree record, @Param("example") DegreeExample example);

    int updateByExample(@Param("record") Degree record, @Param("example") DegreeExample example);

    int updateByPrimaryKeySelective(Degree record);

    int updateByPrimaryKey(Degree record);

//    @author  pby
    int insertBatchDegreeInfo(List<Degree> list);

    // @author pby
    List<AdmissionCard> getDegreeAdmissionCards(@Param("tidOne") int tidOne, @Param("tidTwo") int tidTwo);
}