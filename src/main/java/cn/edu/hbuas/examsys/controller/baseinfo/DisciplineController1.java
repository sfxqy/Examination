package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.AgainstTest1;
import cn.edu.hbuas.examsys.mybatis.pojo.Discipline;
import cn.edu.hbuas.examsys.mybatis.pojo.AgainstTest;
import cn.edu.hbuas.examsys.service.baseinfo.DisciplineService1;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author 牛传喜
 */
@Api(tags = "基础数据-缺考违纪信息管理")
@RequestMapping(value = "/dept")
@RestController
@CrossOrigin

public class DisciplineController1 {

    @Autowired
    private DisciplineService1 disciplineService1;

    @ResponseBody
    @ApiOperation(value = "分页查询所有违纪信息")
    @GetMapping("/disciplines")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int")
    })
    public HashMap<String,Object> getAllDiscipline(Integer pageNum, Integer pageSize, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<AgainstTest> pageInfo= disciplineService1.getAllDisciplineByPage(pageNum,pageSize);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }



    @ResponseBody
    @ApiOperation(value = "分页查询所有缺考信息")
    @GetMapping("/Missdisciplines")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int")
    })
    public HashMap<String,Object> getAllMissTest(Integer pageNum, Integer pageSize,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<AgainstTest> pageInfo= disciplineService1.getAllMissTest(pageNum,pageSize);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }

    @ResponseBody
    @ApiOperation(value = "添加违纪缺考信息前的查询")
    @GetMapping("/disciplineStudent")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "学号", required = true, dataType = "String")
    })
    public HashMap<String,Object> getStudentInfo(String sid) throws Exception
    {
        HashMap<String,Object> hashMap=new HashMap<>();
        List<AgainstTest1> list =disciplineService1.selectStudentInfo(sid);
        hashMap.put("offset",1);
        hashMap.put("total",list.size());
        hashMap.put("rows",list);
        return hashMap;
    }

    @ResponseBody
    @ApiOperation(value = "添加的缺考信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "考试任务编号",value = "disid",required = false,dataType = "int"),
            @ApiImplicitParam(name = "学号",value = "sid",required = true,dataType = "String"),
            @ApiImplicitParam(name = "籍贯",value = "place",required = false,dataType = "String"),
            @ApiImplicitParam(name = "考试科目名称",value = "subjects",required = true,dataType = "String"),
            @ApiImplicitParam(name = "考试日期",value = "dateof",required = false,dataType = "String"),
            @ApiImplicitParam(name = "录入日期",value = "aatime",required = false,dataType = "date"),
            @ApiImplicitParam(name = "违纪原因编号",value = "reason",required = true,dataType = "int"),
            @ApiImplicitParam(name = "处分时长",value = "totaltime",required = true,dataType = "int"),
            @ApiImplicitParam(name = "备注",value = "note",required = true,dataType = "String")
    })
    @PostMapping("/discipline")
    public ResponseData addDiscipline(Discipline discipline)throws Exception
    {

        disciplineService1.addDiscipline(discipline);
        return new ResponseData("添加缺考信息成功",true);
    }

  @ResponseBody
    @ApiOperation(value = "删除违纪信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "违纪编号",value = "id",required = true,dataType = "int")
    })
    @DeleteMapping("/discipline")
    public ResponseData deleteDiscipline(Integer id,HttpSession session)throws Exception
    {
        User user=(User)session.getAttribute("user");
        if(user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足!");
        }
        if (id==null)
        {
            throw new BusinessException("请输入要删除的违纪信息的编号");
        }
        disciplineService1.deleteDiscipline(id);
        return new ResponseData("删除缺考信息成功",true);
    }

   @ResponseBody
    @ApiOperation(value = "修改违纪信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "考试任务编号",value = "disid",required = false,dataType = "int"),
            @ApiImplicitParam(name = "学号",value = "sid",required = false,dataType = "String"),
            @ApiImplicitParam(name = "籍贯",value = "place",required = false,dataType = "String"),
            @ApiImplicitParam(name = "考试科目名称",value = "subjects",required = false,dataType = "String"),
            @ApiImplicitParam(name = "考试日期",value = "dateof",required = false,dataType = "String"),
            @ApiImplicitParam(name = "录入日期",value = "aatime",required = false,dataType = "date"),
            @ApiImplicitParam(name = "违纪原因编号",value = "reason",required = false,dataType = "int"),
            @ApiImplicitParam(name = "处分时长",value = "totaltime",required = false,dataType = "int"),
            @ApiImplicitParam(name = "备注",value = "note",required = false,dataType = "String")
    })
    @PutMapping("discipline")
    public ResponseData updateDiscipline(Discipline discipline,HttpSession session)throws Exception
    {
        User user=(User)session.getAttribute("user");
        if(user.getCid()!=-1 )
        {
            throw new BusinessException("您的权限不足!");
        }
        if (discipline.getSid()==null)
        {
            throw new BusinessException("数据非法");
        }
        disciplineService1.updateDiscipline(discipline);
        return new ResponseData("修改缺考信息成功",true);
    }


    @GetMapping("exportWord")
    @ApiOperation(value = "生成违纪信息文档")
    public void exportWord(HttpServletResponse resp,HttpServletRequest request)throws Exception{
        List<AgainstTest> list=disciplineService1.selectAllAgainstTest();
        System.out.println(list.get(0).getSname()+list.size());
        if (list==null)
        {
            throw new BusinessException("没有违纪信息");
        }
        disciplineService1.creatDisciplineInfo(list,resp,request);
       // return  new ResponseData("生成违纪信息成功",true);


    }

}
