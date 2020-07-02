package cn.edu.hbuas.examsys.controller;


import cn.edu.hbuas.examsys.model.ResponseData;

import cn.edu.hbuas.examsys.mybatis.pojo.Customer;
import cn.edu.hbuas.examsys.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@CrossOrigin
@Api("管理员登入登出")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @ResponseBody
    @ApiOperation(value = "管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(HttpSession session, Customer customer){
        Customer customer1=adminService.Login(session,customer);
        System.out.println(customer1.getCid());
        return new ResponseData(customer1,"登录成功",true);
    }


    @ApiOperation(value = "管理员登出")
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public ResponseData loginOut(HttpSession session){
        session.removeAttribute("user");
        return new ResponseData("登出成功",true);
    }







}
