package cn.edu.hbuas.examsys.controller.createReports;

import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.createReports.CreateReportsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author SFX
 */


@RestController
@CrossOrigin
@Api("生成报表信息")
public class CreateReportsController {

    @Autowired
    private CreateReportsService createReportsService;


    @ApiOperation(value = "生成门贴报表")
    @RequestMapping(value = "/createDoorPost", method = RequestMethod.GET)
    public void createDoorPost(HttpSession session, HttpServletResponse response, String grade, String clazz, String name) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        User user=(User)session.getAttribute("user");
        Integer cid=user.getCid();
        if (cid==0||cid==-1){
            cid=null;
        }
        createReportsService.createDoorPost(session,response,tid,cid,grade,clazz,name);
    }


    @ApiOperation(value = "考试安排表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/createRoomArrangement", method = RequestMethod.GET)
    public void createRoomArrangement(HttpSession session, HttpServletResponse response) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.createRoomArrangement(session, response, tid);
    }





    @ApiOperation(value = "学生信息表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/createStudentStatement", method = RequestMethod.GET)
    public void createStudentStatement(HttpSession session, HttpServletResponse response,Integer cid) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.createStudentStatement(session,response,cid,tid);
    }



    @ApiOperation(value = "生成准考证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/createAdmissionTicket", method = RequestMethod.GET)
    public void createAdmissionTicket(HttpSession session, HttpServletResponse response, Integer cid) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.admissionTicket(session,response,tid,cid);
    }




    @ApiOperation(value = "考试情况记载表")
    @RequestMapping(value = "/createExamDetails", method = RequestMethod.GET)
    public void createExamDetails(HttpSession session, HttpServletResponse response) throws Exception {
        createReportsService.examDetails(session,response);
    }




    @ApiOperation(value = "生成考生座位对照表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/createContrast", method = RequestMethod.GET)
    public void createContrast(HttpSession session, HttpServletResponse response) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.contrast(session,response,tid);
    }



    @ApiOperation(value = "生成教师监考安排表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = false, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "学院编号", required = false, dataType = "int")

    })
    @RequestMapping(value = "/createAroundTest", method = RequestMethod.GET)
    public void createAroundTest(HttpSession session, HttpServletResponse response, Integer cid) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.createAroundTest(tid,cid,response,session);
    }


    @ApiOperation(value = "参考表[学生参考]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "学院编号", required = true, dataType = "int")

    })
    @RequestMapping(value = "/getInfoForStudent", method = RequestMethod.GET)
    public void getInfoForStudent(HttpSession session, HttpServletResponse response,Integer cid) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.getInfoForStudent(response,session,tid,cid);
    }



    @ApiOperation(value = "考试安排Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "考试任务编号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public void excel(HttpSession session,HttpServletResponse response)throws Exception{
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        createReportsService.Excel( session, tid, response);
    }


    /**
     * 秘书下载pdf进行打印
     */
    @ApiOperation(value = "秘书打印填报科目")
    @GetMapping("/printPDF")
    public void printPDF(HttpSession session, HttpServletResponse servletResponse){
        User user = (User) session.getAttribute("user");
        Integer cid = user.getCid();
        Taskplan taskplan = (Taskplan) session.getAttribute("taskplan");
        Integer tid = taskplan.getTid();
        try {
            createReportsService.printPDF(tid,cid,servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}