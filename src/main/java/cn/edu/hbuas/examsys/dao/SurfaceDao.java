package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.SurfaceMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import cn.edu.hbuas.examsys.mybatis.pojo.SurfaceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author SFX
 * 编排完成后的考试安排表：surface
 */
@Repository
public class SurfaceDao {
    @Autowired
    private SurfaceMapper surfaceMapper;

    /**
     * 根据考试任务编号删除所有的考试安排表
     * @param tid
     * @return
     */
    public int deleteByTid(int tid){
        SurfaceExample surfaceExample = new SurfaceExample();
        SurfaceExample.Criteria criteria = surfaceExample.createCriteria();
        criteria.andTidEqualTo(tid);
        return surfaceMapper.deleteByExample(surfaceExample);
    }

    /**
     * 添加一个完成数据
     * @param surface
     * @return
     */
    public int insertOneSurface(Surface surface){
        return surfaceMapper.insert(surface);
    }

    /**
     * 根据主键编号更新数据
     * @param update
     * @return
     */
    public int updateSurfaceByID(Surface update){
        return surfaceMapper.updateByPrimaryKeySelective(update);
    }

    /**
     * 根据条件查询数据数量
     * @param condition
     * @return
     */
    public int countSurfaceByCondition(Surface condition){
        SurfaceExample surfaceExample = new SurfaceExample();
        SurfaceExample.Criteria criteria = surfaceExample.createCriteria();
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getEid())
            criteria.andEidEqualTo(condition.getEid());
        if(null != condition.getRid())
            criteria.andRidEqualTo(condition.getRid());
        return surfaceMapper.countByExample(surfaceExample);
    }
}
