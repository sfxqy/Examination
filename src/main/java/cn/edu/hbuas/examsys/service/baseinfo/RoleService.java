package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.RoleMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Role;
import cn.edu.hbuas.examsys.mybatis.pojo.RoleExample;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 牛传喜
 */

/**
 * 角色信息管理
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 添加角色信息
     * @param role
     */
    public void addRole(Role role) throws Exception
    {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria=roleExample.createCriteria();
        criteria.andRidEqualTo(role.getRid());

        roleExample.or(criteria);
        List<Role> list=roleMapper.selectByExample(roleExample);
        if(list.size()>0)
        {
            throw new BusinessException("该角色已存在!");
        }
        roleMapper.insertSelective(role);
    }

    /**
     * 删除角色信息
     * @param role
     */
    public void deleteRole(Role role) throws Exception
    {
        Role role1=roleMapper.selectByPrimaryKey(role.getRid());
        if(role1==null)
        {
            throw new BusinessException("不存在该角色 ");
        }
        roleMapper.deleteByPrimaryKey(role.getRid());
    }

    /**
     * 修改角色信息
     * @param role
     */
    public void updateRole(Role role) throws Exception
    {
        Role role1=roleMapper.selectByPrimaryKey(role.getRid());
        if(role1==null)
        {
            throw new BusinessException("不存在该角色");
        }
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 查询所有角色信息
     * @return
     */
    public List<Role> getAllRole() throws Exception
    {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andRidIsNotNull();
        List<Role> roles=roleMapper.selectByExample(example);
        if (roles.size()==0)
        {
            throw new BusinessException("没有任何角色信息...");
        }
        return roles;
    }

    /**
     * 根据id查询角色信息
     * @return
     */
    public Role getRoleById(Role role) throws Exception
    {
        Role role1=roleMapper.selectByPrimaryKey(role.getRid());
        if(role1==null)
        {
            throw new BusinessException("没有查询到该角色");
        }
        return roleMapper.selectByPrimaryKey(role.getRid());
    }
}
