
package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Profession;
import cn.edu.hbuas.examsys.service.baseinfo.ProfessionService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author 牛传喜
 */

@Api(tags = "基础数据-专业信息管理")
@RequestMapping("/dept")
@RestController
@CrossOrigin
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;



    @ResponseBody
    @ApiOperation("添加专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "专业编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "cid",value = "学院编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "pname",value = "专业名称",required = true,dataType = "String")
    })
    @PostMapping("/profession")
    public ResponseData addProfession(Profession profession, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=profession.getCid())
        {
            throw new BusinessException("您不能添加其他学院的专业信息！");
        }
        if(profession.getPid()==null )
            throw new BusinessException("数据非法");
        professionService.addProfession(profession);
        ResponseData   responseData = new ResponseData("添加专业信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("删除专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "专业编号",required = true,dataType = "int"),
    })
    @DeleteMapping("/profession")
    public ResponseData deleteProfession(Profession profession,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=profession.getCid())
        {
            throw new BusinessException("您不能删除其他学院的专业信息！");
        }
        if(professionService.selectProfessionById(profession)==null)
        {
            throw new BusinessException("不存在该专业信息!");
        }
        professionService.deleteProfession(profession.getPid());
        ResponseData responseData=new ResponseData("删除专业信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("修改专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "专业编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "cid",value = "学院编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "pname",value = "专业名称",required = true,dataType = "String")
    })
    @PutMapping("profession")
    public ResponseData updateProfession(Profession profession,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=profession.getCid())
        {
            throw new BusinessException("您不能修改其他学院的专业信息！");
        }
        if(profession==null)
        {
            throw new BusinessException("未做修改!");
        }
        professionService.updateRoomInfo(profession);
        ResponseData responseData=new ResponseData("修改专业信息成功",true);
        return  responseData;
    }

    @ResponseBody
    @ApiOperation("查询所有专业信息")
    @GetMapping("professions")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int")
    })
    public HashMap<String,Object> selectProfession(Integer pageNum,Integer pageSize,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足!");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<Profession> pageInfo=professionService.selectAllProfession(pageNum,pageSize);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }

    @ResponseBody
    @ApiOperation("根据id查询专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "专业编号",required = true,dataType = "int"),
    })
    @GetMapping("/profession")
    public ResponseData selectProById(Profession profession,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=profession.getCid())
        {
            throw new BusinessException("您的权限不足!");
        }
        if(profession.getPid()==null)
        {
            throw new BusinessException("请输入要查询的专业编号");
        }
        ResponseData responseData=new ResponseData("查询专业信息成功",true);
        responseData.setData(professionService.selectProfessionById(profession));
        return  responseData;
    }

    @ResponseBody
    @ApiOperation("根据学院查询专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",value = "学院编号",required = true,dataType = "int"),
    })
    @GetMapping("/pname")
    public ResponseData selectPnameByCid(Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您的不能查看非本院的专业信息!");
        }
        if(cid==null)
        {
            throw new BusinessException("请输入要查询的学院编号");
        }
        ResponseData responseData=new ResponseData("查询专业信息成功",true);
        responseData.setRows(professionService.selectPnameByCid(cid));
        responseData.setTotal((long)professionService.selectPnameByCid(cid).size());
        return  responseData;
    }
}

