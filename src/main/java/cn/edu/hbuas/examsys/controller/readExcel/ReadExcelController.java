package cn.edu.hbuas.examsys.controller.readExcel;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.readExcel.ReadExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author SFX
 */


@RestController
@CrossOrigin
@Api("解析Excel")
public class ReadExcelController {

    @Autowired
    private ReadExcelService readExcelService;

    @ApiOperation(value = "管理员批量上传教师信息")
    @RequestMapping(value = "/readTeacher", method = RequestMethod.POST)
    public ResponseData readTeacher(@ApiParam(value = "上传教师信息Excel", required = true) MultipartFile file, HttpSession session) throws Exception {
        readExcelService.readTeacher(session, file);
        return new ResponseData("上传成功", true);
    }



    @ApiOperation(value = "管理员批量上传各院考试安排信息")
    @RequestMapping(value = "/readExamcourse", method = RequestMethod.POST)
    public ResponseData readExamcourse(@ApiParam(value = "上传各院考试安排信息Excel",required = true) MultipartFile file, HttpSession session) throws Exception{
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        readExcelService.readExamcourse(session,file,tid);
        return new ResponseData("上传成功",true);
    }


    @ApiOperation(value = "管理员批量上传教室信息")
    @RequestMapping(value = "/readRoom", method = RequestMethod.POST)
    public ResponseData readRoom(@ApiParam(value = "上传教室信息Excel", required = true) MultipartFile file, HttpSession session) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        readExcelService.readRoom(session, file,tid);
        return new ResponseData("上传成功", true);
    }




    @ApiOperation(value = "管理员批量上传院别专业信息")
    @RequestMapping(value = "/readProfession", method = RequestMethod.POST)
    public ResponseData readProfession(@ApiParam(value = "上传院别专业表", required = true) MultipartFile file, HttpSession session) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        readExcelService.readProfession(session, file);
        return new ResponseData("上传成功", true);
    }







    @ApiOperation(value = "管理员批量上传重修信息")
    @RequestMapping(value = "/readReset", method = RequestMethod.POST)
    public ResponseData readReset(@ApiParam(value = "上传重修信息", required = true) MultipartFile file,HttpSession session) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        readExcelService.readReset(session, file,tid,taskplan);
        return new ResponseData("上传成功", true);
    }
}
