package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherAndRole;
import cn.edu.hbuas.examsys.service.baseinfo.CollegeManagerService;
import cn.edu.hbuas.examsys.service.baseinfo.TeacherService;
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
import java.util.List;

/**
 * @author 牛传喜
 */

@Api(tags = "基础数据-教师信息管理")
@RequestMapping(value="/dept")
@CrossOrigin
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CollegeManagerService collegeManagerService;

    @ResponseBody
    @ApiOperation(value = "添加教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="number" ,value = "教师工号",required =true ,dataType ="String"),
            @ApiImplicitParam(name ="tname" ,value = "教师姓名",required =true ,dataType ="String"),
            @ApiImplicitParam(name ="sex" ,value = "教师性别",required =false ,dataType ="String"),
            @ApiImplicitParam(name ="wages" ,value = "工资号",required =true ,dataType ="String"),
            @ApiImplicitParam(name ="phone" ,value = "手机号",required =true ,dataType ="String"),
            @ApiImplicitParam(name ="cid" ,value = "学院编号",required =true ,dataType ="int"),
            @ApiImplicitParam(name ="rid" ,value = "角色编号",required =true ,dataType ="int"),
            @ApiImplicitParam(name ="times" ,value = "监考次数",required =true ,dataType ="int")
    })
   @PostMapping("/teacher")
    public ResponseData addTeacher(Teacher teacher, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if(user.getCid()!=-1 && user.getCid()!=teacher.getCid())
        {
            throw new BusinessException("您不能添加非本院教师的信息");
        }
        if(teacher.getNumber()==null)
        {
            throw new BusinessException("数据非法");
        }

        teacherService.addTeacher(teacher);
        ResponseData responseData=new ResponseData("添加教师信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "根据教师编号删除教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="tid" ,value = "教师编号",required =true ,dataType ="String")
    })
    @DeleteMapping("/teacher")
    public ResponseData deleteTeacher(String tid, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        List<Teacher> list=teacherService.selectTeachers(tid);
        if(user.getCid()!=-1 )
        {
            for (int i=0;i<list.size();i++)
            {
                if (list.get(i).getCid()!=user.getCid())
                {
                    throw new BusinessException("您不能删除非本院教师的信息");
                }
            }

        }
        teacherService.deleteTeacher(tid);
        ResponseData responseData=new ResponseData("删除教师信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "删除整个院的教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="cid" ,value = "学院编号",required =true ,dataType ="int")
    })
    @DeleteMapping("/teacherByCid")
    public ResponseData deleteTeacherByCid(Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您不能删除其他学院的教师信息");
        }
        teacherService.deleteTeacherByCid(cid);
        ResponseData responseData=new ResponseData("删除整个院的教师信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "删除所有教师信息")
    @DeleteMapping("/allTeacher")
    public ResponseData deleteAllTeacher(HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足");
        }
        teacherService.deleteAllTeacher();
        ResponseData responseData=new ResponseData("删除所有教师信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("修改教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="tid" ,value = "教师编号",required =true ,dataType ="String")
    })
    @PutMapping("/teacher")
    public ResponseData updateTeacher(Teacher teacher,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=teacher.getCid())
        {
            throw new BusinessException("您不能修改其他学院的教师信息");
        }
        if(teacher==null)
        {
            throw new BusinessException("该老师不存在!");
        }
        teacherService.updateTeacherInfo(teacher);
        ResponseData responseData =new ResponseData("修改教师信息成功",true);
        return responseData;
    }



    @ResponseBody
    @ApiOperation(value = "分页查询所有教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "院别编号", required = false, dataType = "int"),
            @ApiImplicitParam(name = "tname", value = "教师姓名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "number", value = "教师工号", required = false, dataType = "String")
    })
    @GetMapping("teachersPage")
    public HashMap<String,Object> selectTeachersByPage(Integer pageNum, Integer pageSize, Integer cid,String tname,String number,HttpSession session) throws Exception
    {
        /*User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足");
        }*/
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<TeacherAndRole> pageInfo =teacherService.getAllTeachersByPage(pageNum,pageSize,cid,tname,number);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return hashMap;
    }

    @ResponseBody
    @ApiOperation(value = "分页查询本院所有教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = false, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "院别编号", required = false, dataType = "int")
    })
    @GetMapping("selfTeachers")
    public HashMap<String,Object> selectSelfTeachers(Integer pageNum, Integer pageSize, Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您不能查看其他学院的教师信息");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<TeacherAndRole> pageInfo =teacherService.selectSelfTeacherByPage(pageNum,pageSize,cid);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return hashMap;
    }

    @ResponseBody
    @ApiOperation(value = "不分页查询本院所有教师信息")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "cid", value = "院别编号", required = false, dataType = "int")
    })
    @GetMapping("getSelfTeachers")
    public HashMap<String,Object> selectAllTeachers(Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您不能查询其他学院的教师信息");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        List<TeacherAndRole> list =teacherService.selectSelfTeacher(cid);
        hashMap.put("offset",1);
        hashMap.put("total",list.size());
        hashMap.put("rows",list);
        return hashMap;
    }
}
