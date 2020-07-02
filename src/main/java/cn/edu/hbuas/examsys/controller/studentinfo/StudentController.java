package cn.edu.hbuas.examsys.controller.studentinfo;


import cn.edu.hbuas.examsys.model.Constants;
import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.DegreeService;
import cn.edu.hbuas.examsys.service.studentinfo.StudentService;
import io.swagger.annotations.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author SFX
 */


@RestController
@RequestMapping("/court")
@CrossOrigin
@Api("学生信息管理")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @Autowired
    private DegreeService degreeService;


    @ApiOperation(value = "管理员批量上传学生信息")
    @RequestMapping(value = "/uploadStudentInfo", method = RequestMethod.POST)
    public ResponseData uploadStudentInfo(@ApiParam(value = "上传Excel",required = true) MultipartFile file, HttpSession session) throws Exception{
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        if (taskplan.getType().equals(Constants.DEGREE_EXAMINATION)){
            //调用学位考试的上传方法
            degreeService.uploadDegreeInfo(tid,file,session);
        }else {
            //调用常规考试的上传方法
            studentService.uploadStudentInfo(tid,file,session);
        }
        return new ResponseData("学生信息已保存",true);
    }


    @ApiOperation(value = "管理员批量上传学生照片")
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public ResponseData uploadPhoto(HttpSession session,@RequestParam("file") MultipartFile[] file)throws Exception{
        studentService.uploadPhoto(session,file);
        return new ResponseData("照片上传成功",true);
    }


    @ApiOperation(value = "管理员批量下载学生照片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "院别编号", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pid", value = "专业编号", required = false, dataType = "int"),
            @ApiImplicitParam(name = "grade", value = "年级", required = false, dataType = "String"),
            @ApiImplicitParam(name = "clazz", value = "班级", required = false, dataType = "String"),
    })
    @RequestMapping(value = "/downloadPhoto",method = RequestMethod.POST)
    public void downloadPhoto(HttpServletResponse response,HttpSession session,Integer cid, Integer pid, String grade, String clazz)throws Exception {
        studentService.download(response,session,cid,pid,grade,clazz);
    }




}
