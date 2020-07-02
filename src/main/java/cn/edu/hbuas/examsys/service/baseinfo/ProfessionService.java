package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.ProfessionMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Profession;
import cn.edu.hbuas.examsys.mybatis.pojo.ProfessionExample;
import cn.edu.hbuas.examsys.mybatis.pojo.Room;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 牛传喜
 */

/**
 * 考场信息
 */
@Service
public class ProfessionService {

    @Autowired
    private ProfessionMapper professionMapper;


    /**
     * 添加专业信息
     * @param profession
     */
    public void addProfession(Profession profession) throws Exception
    {
        ProfessionExample professionExample = new ProfessionExample();
        ProfessionExample.Criteria criteria = professionExample.createCriteria();
        criteria.andPidEqualTo(profession.getPid());


        professionExample.or(criteria);
        List<Profession> professions = professionMapper.selectByExample(professionExample);
        if(professions.size() > 0){

            throw new BusinessException("专业信息已经存在!");
        }
        professionMapper.insertSelective(profession);

    }


    /**
     * 根据主键删除专业信息
     * @param pid
     * @throws Exception
     */
    public void deleteProfession(Integer pid)
    {
        professionMapper.deleteByPrimaryKey(pid);
    }

    /**
     * 分页查询所有专业信息
     *
     */
    public PageInfo<Profession> selectAllProfession(Integer pageNum,Integer pageSize)throws Exception
    {
        if(null==pageNum || pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);
        List<Profession> professionList=professionMapper.selectByExample(null);
        if(professionList.size()==0)
        {
            throw new BusinessException("没有相关信息...");
        }
        PageInfo<Profession> pageInfo=new PageInfo<>(professionList);
        return pageInfo;
    }

    /**
     * 修改专业信息
     */
    public void updateRoomInfo(Profession profession)
    {
        professionMapper.updateByPrimaryKey(profession);
    }

    /**
     * 根据编号查询专业信息
     */
    public Profession selectProfessionById(Profession profession) throws Exception
    {
        Profession profession1=professionMapper.selectByPrimaryKey(profession.getPid());
        if(profession1==null)
        {
                throw new BusinessException("不存在该专业信息!");
        }
        return profession1;
    }

    /**
     * 根据学院编号查询专业信息
     */
    public List<Profession> selectPnameByCid(Integer cid) throws Exception
    {
        if (cid==null)
        {
            throw new BusinessException("请输入学院编号");
        }
        List<Profession> list=professionMapper.selectPnameByCid(cid);
        return list;
    }
}
