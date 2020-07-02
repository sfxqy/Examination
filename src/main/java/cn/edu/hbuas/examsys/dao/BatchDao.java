package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.BatchMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Batch;
import cn.edu.hbuas.examsys.mybatis.pojo.BatchExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 同批考试表操作
 */

@Repository
public class BatchDao {
    @Autowired
    BatchMapper batchMapper;

    /**
     * 根据条件删除同批考试数据，如果条件属性字段为 NULL，则不参与筛选
     * @param condition
     * @return
     */
    public int deleteDetailsByCondition(Batch condition){
        BatchExample batchExample = new BatchExample();
        BatchExample.Criteria criteria = batchExample.createCriteria();
        if(null != condition.getBid())
            criteria.andBidEqualTo(condition.getBid());
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getBname())
            criteria.andBnameEqualTo(condition.getBname());
        if(null != condition.getGrade())
            criteria.andGradeEqualTo(condition.getGrade());
        if(null != condition.getType())
            criteria.andTypeEqualTo(condition.getType());
        return batchMapper.deleteByExample(batchExample);
    }

    /**
     * 根据主键删除同批考试
     * @param id
     * @return
     */
    public int deleteBatchByID(Integer id){
        return batchMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键批量删除同批考试
     * @param batches
     * @return
     */
    public int deleteBatchListByID(List<Batch> batches){
        for (Batch batch:batches){
            batchMapper.deleteByPrimaryKey(batch.getBid());
        }
        return batches.size();
    }


    /**
     * 根据条件查找同批考试数据
     * @param condition
     * @return
     */
    public List<Batch> selectDetailsByCondition(Batch condition){
        BatchExample batchExample = new BatchExample();
        BatchExample.Criteria criteria = batchExample.createCriteria();
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getBname())
            criteria.andBnameEqualTo(condition.getBname());
        if(null != condition.getGrade())
            criteria.andGradeEqualTo(condition.getGrade());
        if(null != condition.getType())
            criteria.andTypeEqualTo(condition.getType());

        return batchMapper.selectByExample(batchExample);
    }

    /**
     * 添加同批考试数据
     * @param batch
     * @return
     */
    public int insertBatch(Batch batch){
        return batchMapper.insertSelective(batch);
    }
}
