package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.CollegeMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.College;
import cn.edu.hbuas.examsys.mybatis.pojo.CollegeExample;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.CheckUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 学院信息管理
 */
@Service
public class CollegeManagerService {
    private static final Logger logger = LoggerFactory.getLogger(CollegeManagerService.class);

    @Autowired
    private CollegeMapper collegeMapper;

    /**
     * 分页查询
     * pageNum页面号，pageSize页面条数
     * info为模糊查询的参数
     */
    public PageInfo<College> getAllcollegesByPage(Integer pageNum,Integer pageSize,String info)
    {
        if (  null==pageNum || pageNum <= 0) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,pageSize);
        CollegeExample collegeExample=new CollegeExample();
        CollegeExample.Criteria criteria=collegeExample.createCriteria();
        if (info!=null)
            criteria.andCollegeLike("%"+info+"%");
        List<College> collegeList=collegeMapper.selectByExample(collegeExample);
        PageInfo<College> pi=new PageInfo<College>(collegeList);
        return pi;
    }

    /**
     * 添加学院
     * @param college
     */
    public void addCollege(College college) throws Exception{
        logger.info("添加学院信息 : "+college);
        CollegeExample collegeExample = new CollegeExample();
        CollegeExample.Criteria criteria = collegeExample.createCriteria();
        criteria.andCollegeEqualTo(college.getCollege());

        CollegeExample.Criteria criteria1 = collegeExample.createCriteria();
        criteria1.andCidEqualTo(college.getCid());

        collegeExample.or(criteria1);
        List<College> colleges = collegeMapper.selectByExample(collegeExample);
        if(colleges.size() > 0){
            logger.info("学院信息已经存在 : "+colleges.get(0));
            throw new BusinessException("学院信息已经存在");
        }
        collegeMapper.insertSelective(college);
    }

    /**
     * 根据学院主键删除学院
     * @param id
     * @throws Exception
     */
    public void deleteCollegeById(Integer id) throws Exception{
        if(id==null)
        {
            throw new BusinessException("请输入要删除的编号");
        }
        if (collegeMapper.selectByPrimaryKey(id)==null)
        {
            throw new BusinessException("不存在该学院");
        }
        collegeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过学院编号查找学院
     * @return
     */
    public College getCollegeById(College college) throws Exception{
        CollegeExample example = new CollegeExample();
        CollegeExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(college.getId());
        List<College> colleges = collegeMapper.selectByExample(example);
        if(colleges.size() > 0){
            return colleges.get(0);
        }else {
            throw new BusinessException("信息查找失败");
        }
    }

  /*  *//**
     * 查询全部的学院信息
     * @return
     * @throws Exception
     *//*
    public List<College> getAllColleges() throws Exception{
        CollegeExample example = new CollegeExample();
        CollegeExample.Criteria criteria = example.createCriteria();
        criteria.andCollegeIsNotNull();
        List<College> colleges = collegeMapper.selectByExample(example);
        return colleges;
    }
*/
}
