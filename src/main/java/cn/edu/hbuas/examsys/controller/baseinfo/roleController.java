package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Role;
import cn.edu.hbuas.examsys.service.baseinfo.RoleService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 牛传喜
 */

@Api(tags = "基础数据-角色信息管理")
@RestController
@RequestMapping("/dept")
public class roleController {
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @ApiOperation(value = "添加角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "角色编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "rname",value = "角色名",required = true,dataType = "String"),

    })
    @PostMapping("role")
    public ResponseData addRole(Role role, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(role.getRname()==null)
        {
            throw new BusinessException("数据非法");
        }
        roleService.addRole(role);
        return new ResponseData("添加角色信息成功",true);
    }

    @ResponseBody
    @ApiOperation(value = "删除角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "角色编号",required = true,dataType = "int")
    })
    @DeleteMapping("role")
    public ResponseData deleteRole(Role role,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(role.getRid()==null)
        {
            throw new BusinessException("不存在该角色");
        }
        roleService.deleteRole(role);
        return new ResponseData("删除角色信息成功",true);
    }

    @ResponseBody
    @ApiOperation(value = "修改角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rname",value = "角色名",required = true,dataType = "String"),
    })
    @PutMapping("role")
    public ResponseData updateRole(Role role,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(role.getRid()==null)
        {
            throw new BusinessException("数据非法");
        }
        roleService.updateRole(role);
        return new ResponseData("修改角色信息成功",true);
    }

    @ResponseBody
    @ApiOperation(value = "查询所有角色信息")
    @GetMapping("roles")
    public ResponseData getAllRole(HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        ResponseData responseData=new ResponseData("查询所有角色信息成功",true);
        responseData.setData(roleService.getAllRole());
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "根据id查询角色信息")
    @GetMapping("role")
    public ResponseData getRoleById(Role role,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        ResponseData responseData=new ResponseData("查询角色信息成功",true);
        responseData.setData(roleService.getRoleById(role));
        return responseData;
    }
}
