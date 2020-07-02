package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Details;
import cn.edu.hbuas.examsys.mybatis.pojo.DetailsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface DetailsMapper {
    int countByExample(DetailsExample example);

    int deleteByExample(DetailsExample example);

    int deleteByPrimaryKey(Integer did);

    int insert(Details record);

    int insertSelective(Details record);

    List<Details> selectByExample(DetailsExample example);

    Details selectByPrimaryKey(Integer did);

    int updateByExampleSelective(@Param("record") Details record, @Param("example") DetailsExample example);

    int updateByExample(@Param("record") Details record, @Param("example") DetailsExample example);

    int updateByPrimaryKeySelective(Details record);

    int updateByPrimaryKey(Details record);

//    @author  pby
    void insertBatchDetails(ArrayList<Details> list);
//   @author pby
    List<Details> selectByTid(@Param("tid") int tid);
}