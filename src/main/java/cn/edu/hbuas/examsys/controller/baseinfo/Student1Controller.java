
package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentAndCollege;
import cn.edu.hbuas.examsys.service.baseinfo.Student1Service;
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

/**
 * @author 牛传喜
 */

@Api(tags = "基础数据-学生信息管理")
@RequestMapping("/dept")
@RestController
@CrossOrigin
public class Student1Controller {


    private static final Logger logger = LoggerFactory.getLogger(Student1Controller.class);
    @Autowired
    private Student1Service student1Service;



    @ResponseBody
    @ApiOperation(value = "添加学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid",value = "学号",required = true,dataType = "String"),
            @ApiImplicitParam(name = "sname",value = "姓名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "tid",value = "用于区分",required = true,dataType = "int"),
            @ApiImplicitParam(name = "sex",value = "性别",required = false,dataType = "String"),
            @ApiImplicitParam(name = "grade ",value = "年级",required = true,dataType = "String"),
            @ApiImplicitParam(name = "clazz",value = "班级",required = true,dataType = "String"),
            @ApiImplicitParam(name = "pid",value = "专业编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "cid",value = "学院编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "idcard",value = "身份证号",required = false,dataType = "String"),
            @ApiImplicitParam(name = "photo",value = "照片",required = false,dataType = "String"),
            @ApiImplicitParam(name = "state",value = "学生状态",required = false,dataType = "int")
    })
    @PostMapping("/student")
    public ResponseData addStudent(String sid, String sname, Integer tid, String sex, String grade, String clazz, Integer pid, Integer cid, String idcard, String photo, Integer state, HttpSession session)
            throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您不能添加非本院的学生信息");
        }
        Student student=new Student();
        student.setSid(sid);
        student.setSname(sname);
        student.setTid(tid);
        student.setSex(sex);
        student.setGrade(grade);
        student.setClazz(clazz);
        student.setPid(pid);
        student.setCid(cid);
        student.setIdcard(idcard);
        student.setPhoto(photo);
        student.setState(state);
        logger.info("{}  {}  {}  {}  {}  {}  {}  {}  {}  {}",sid,sname,sex ,grade, clazz, pid, cid, idcard, photo, state);
        student1Service.addStudent(student);
        ResponseData   responseData = new ResponseData("添加学生信息成功",true);

        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "根据学号删除学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid",value = "学号",required = true,dataType = "String"),
    })
    @DeleteMapping("/student")
    public ResponseData deleteStudent(String sid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        List<Student> list=student1Service.selectStudents(sid);
        if (user.getCid()!=-1 )
        {
            for (int i=0;i<list.size();i++)
            {
                if (user.getCid()!=list.get(i).getCid())
                    throw new BusinessException("您不能删除非本院的学生信息");
            }

        }
        student1Service.deleteStudentById(sid);
        ResponseData responseData=new ResponseData("删除学生信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "根据学院编号删除学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",value = "学院编号",required = true,dataType = "int"),
    })
    @DeleteMapping("/studentByCid")
    public ResponseData deleteStudentByCid(Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您不能删除非本院的学生信息");
        }
        student1Service.deleteStudentByCid(cid);
        ResponseData responseData=new ResponseData("删除整个学院学生信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "删除所有学生信息")
    @DeleteMapping("/Allstudent")
    public ResponseData deleteAllStudent(HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        student1Service.deleteAllStudent();
        ResponseData responseData=new ResponseData("删除所有学生信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation(value = "修改学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid",value = "学号",required = true,dataType = "String")
    })
    @PutMapping("student")
    public ResponseData updateStudent(Student student,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=student.getCid())
        {
            throw new BusinessException("您不能修改非本院的学生信息");
        }
        if(student==null)
        {
            throw new BusinessException("未做修改");
        }
        student1Service.updateStudentInfo(student);
        ResponseData responseData=new ResponseData("修改学生信息成功",true);
        return  responseData;
    }




    @ResponseBody
    @ApiOperation(value = "分页查询所有学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "sname", value = "学生姓名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "cid", value = "学院编号", required = false, dataType = "int")
    })
    @GetMapping("studentsPage")
    public HashMap<String,Object> selectStudentsByPage(Integer pageNum, Integer pageSize,String sname,Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 )
        {
            throw new BusinessException("您的权限不足！");
        }
        logger.info("{},{}",sname,cid);
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<StudentAndCollege> pageInfo=student1Service.getAllStudentsByPage(pageNum,pageSize,sname,cid);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }

    @ResponseBody
    @ApiOperation(value = "分页查询本院学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "学院编号", required = true, dataType = "int")
    })
    @GetMapping("selfStudentsPage")
    public HashMap<String,Object> selectSelfStudentsByPage(Integer pageNum, Integer pageSize,Integer cid,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1 && user.getCid()!=cid)
        {
            throw new BusinessException("您不能查询非本院的学生信息");
        }
        logger.info("{},{}",cid);
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<StudentAndCollege> pageInfo=student1Service.getSelfStudent(pageNum,pageSize,cid);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }
}

