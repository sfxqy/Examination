package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Customer;
import cn.edu.hbuas.examsys.service.baseinfo.CustomerService;
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

@Api(tags = "基础数据-用户信息管理")
@RestController
@RequestMapping("/dept")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @ApiOperation(value = "添加用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "cid",value = "院别编号",required = true,dataType = "int"),
    })
    @PostMapping("customer")
    public ResponseData addCustomer(Customer customer, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(customer.getAccount()==null)
        {
            throw new BusinessException("数据非法!");
        }
        customerService.addCustomer(customer);
        return new ResponseData("添加用户信息成功",true);
    }

    @ResponseBody
    @ApiOperation(value = "删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户编号",required = true,dataType = "int")
    })
    @DeleteMapping("customer")
    public ResponseData deleteCustomer(Customer customer,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(customer.getId()==null)
        {
            throw new BusinessException("请输入要删除的考场信息的编号");
        }
        customerService.deleteCustomer(customer);
        return new ResponseData("删除用户信息成功",true);
    }

    @ResponseBody
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "cid",value = "院别编号",required = true,dataType = "int"),
    })
    @PutMapping("customer")
    public ResponseData updateCustomer(Customer customer,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(customer.getAccount()==null)
        {
            throw new BusinessException("数据非法");
        }
        customerService.updateCustomer(customer);
        return new ResponseData("修改用户信息成功",true);
    }

    @ResponseBody
    @ApiOperation(value = "查询所有用户信息")
    @GetMapping("customers")
    public ResponseData getAllCustomer(HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        ResponseData responseData=new ResponseData("查询用户信息成功",true);
        responseData.setData(customerService.getAllCustomer());
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "根据id查询用户信息")
    @GetMapping("customer")
    public ResponseData getCustomerById(Customer customer,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        ResponseData responseData=new ResponseData("查询用户信息成功",true);
        responseData.setData(customerService.getCustomer(customer));
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "分页查询用户信息")
    @GetMapping("customersPage")
    public ResponseData getCustomerByPage(Integer pageNum,Integer pageSize,String info,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        ResponseData responseData=new ResponseData("查询用户信息成功",true);
        responseData.setTotal((long)customerService.getAllCustomer().size());
        responseData.setRows(customerService.getCustomersByPage(pageNum,pageSize,info));
        return responseData;
    }
}
