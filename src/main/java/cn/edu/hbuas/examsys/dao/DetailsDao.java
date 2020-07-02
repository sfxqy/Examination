package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.DetailsMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Details;
import cn.edu.hbuas.examsys.mybatis.pojo.DetailsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author SFX
 * 编排完成学生详细信息
 */
@Repository
public class DetailsDao {
    @Autowired
    private DetailsMapper detailsMapper;

    /**
     * 添加一个详情信息
     * @param details
     * @return
     */
    public int insertDetails(Details details){
        return detailsMapper.insertSelective(details);
    }

    /**
     * 通过主键删除一个详情
     * @param did
     * @return
     */
    public int deleteOneDetailsByID(int did){
        return detailsMapper.deleteByPrimaryKey(did);
    }

    /**
     * 根据条件删除详情
     * @param condition 查询条件
     * @return
     */
    public int deleteDetailsesByCondition(Details condition){
        DetailsExample detailsExample = new DetailsExample();
        DetailsExample.Criteria criteria = detailsExample.createCriteria();
        if(null != condition.getSid())
            criteria.andSidEqualTo(condition.getSid());
        if(null != condition.getPname())
            criteria.andPnameEqualTo(condition.getPname());
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getEid())
            criteria.andEidEqualTo(condition.getEid());
        return detailsMapper.deleteByExample(detailsExample);
    }

    /**
     * 根据条件删除详情
     * @param condition 查询条件
     * @return
     */
    public int countDetailsesByCondition(Details condition){
        DetailsExample detailsExample = new DetailsExample();
        DetailsExample.Criteria criteria = detailsExample.createCriteria();
        if(null != condition.getSid())
            criteria.andSidEqualTo(condition.getSid());
        if(null != condition.getPname())
            criteria.andPnameEqualTo(condition.getPname());
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getEid())
            criteria.andEidEqualTo(condition.getEid());
        return detailsMapper.deleteByExample(detailsExample);
    }
}
