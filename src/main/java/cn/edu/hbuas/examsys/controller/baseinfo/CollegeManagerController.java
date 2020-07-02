package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.College;
import cn.edu.hbuas.examsys.service.baseinfo.CollegeManagerService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;


@RestController
@Api(tags = "基础数据-学院信息管理")
@RequestMapping(value = "/dept")
public class CollegeManagerController {
    private static final Logger logger = LoggerFactory.getLogger(CollegeManagerController.class);
    @Autowired
    private CollegeManagerService collegeManagerService;



    @ApiOperation(value = "分页查询所有学院信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "info", value = "学院名称", required = false, dataType = "String"),
    })
    @GetMapping("/collegePage")
    public HashMap<String,Object> getAllCollege(Integer pageNum, Integer pageSize, String info, HttpSession session) throws Exception{
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<College> pageInfo=collegeManagerService.getAllcollegesByPage(pageNum,pageSize,info);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return hashMap;
    }


    @ResponseBody
    @ApiOperation(value = "添加学院信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",value = "学院编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "college",value = "学院名称",required = true,dataType = "String")
    })
    @PostMapping("/college")
    public ResponseData addCollegeInfo(College college,HttpSession session) throws Exception{
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(college.getCid()==null)
        {
            throw new BusinessException("请输入学院名称");
        }
        if(college.getCollege()==null)
        {
            throw new BusinessException("请输入学院名称");
        }
        collegeManagerService.addCollege(college);
        ResponseData responseData = new ResponseData("添加学院信息成功",true);
        return responseData;
    }

    @ApiOperation(value = "根据主键删除学院信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "学院编号主键",required = true,dataType = "int")
    })
    @DeleteMapping("/college")
    public ResponseData deleteCollegeInfo(Integer id,HttpSession session) throws Exception{
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }

        collegeManagerService.deleteCollegeById(id);
        ResponseData responseData = new ResponseData("删除学院信息成功",true);
        return responseData;
    }

    @GetMapping("/college")
    @ApiOperation(value = "根据主键获取学院信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键",required = true,dataType = "int")
    })
    public ResponseData getCollegeInfo(College college,HttpSession session) throws Exception{
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }

        College college1 = collegeManagerService.getCollegeById(college);
        ResponseData responseData = new ResponseData("获取学院信息成功",true);
        responseData.setRows(college1);
        responseData.setTotal(1l);
        return responseData;
    }

}
