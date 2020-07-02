package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.SurfaceMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import cn.edu.hbuas.examsys.mybatis.pojo.SurfaceExample;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 牛传喜
 * 对基础数据中的考试信息进行管理
 */
@Service
public class ExamInfoService {
    @Autowired
    private SurfaceMapper surfaceMapper;

    /**
     * 查询全部的考试信息
     * @return
     */
    public List<Surface> getAllData(){
        SurfaceExample example = new SurfaceExample();
        SurfaceExample.Criteria criteria = example.createCriteria();
        criteria.andSuidIsNotNull();
        return surfaceMapper.selectByExample(example);
    }

    /**
     * 根据考试信息编号删除信息
     * @param surface
     */
    public void deleteDataByID(Surface surface) throws Exception{
        Surface data = surfaceMapper.selectByPrimaryKey(surface.getTid());
        if (null == data){
            throw new BusinessException("数据不存在");
        }
        surfaceMapper.deleteByPrimaryKey(surface.getTid());
    }

    /**
     * 根据主键修改数据
     * @param surface
     */
    public void updateDataByID(Surface surface) throws Exception{
        Surface data = surfaceMapper.selectByPrimaryKey(surface.getTid());
        if (null == data){
            throw new BusinessException("数据不存在");
        }
        surfaceMapper.updateByPrimaryKeySelective(surface);
    }

    /**
     * 添加考场信息
     * @param surface
     */
    public void addData(Surface surface){
        surfaceMapper.insertSelective(surface);
    }
}
