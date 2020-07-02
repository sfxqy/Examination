package cn.edu.hbuas.examsys.controller.createReports;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

/**
 * @author SFX
 */

@RestController
@CrossOrigin
@Api("报表是否生成信息接口")
public class ReportsInfo {




    @ApiOperation(value = "门贴生成状态接口")
    @RequestMapping(value = "/doorPostInfo", method = RequestMethod.GET)
    public ResponseData doorPostInfo(HttpSession session) {
        String door=(String) session.getAttribute("DoorPost");
        return new ResponseData(door,true);
    }



    @ApiOperation(value = "考试安排表")
    @RequestMapping(value = "/roomArrangementInfo", method = RequestMethod.GET)
    public ResponseData roomArrangementInfo(HttpSession session) {
        String door=(String) session.getAttribute("roomArrangement");
        return new ResponseData(door,true);
    }


    @ApiOperation(value = "学生信息表")
    @RequestMapping(value = "/studentStatementInfo", method = RequestMethod.GET)
    public ResponseData studentStatementInfo(HttpSession session) {
        String door=(String) session.getAttribute("studentStatement");
        return new ResponseData(door,true);
    }


    @ApiOperation(value = "生成准考证")
    @RequestMapping(value = "/admissionTicketInfo", method = RequestMethod.GET)
    public ResponseData admissionTicketInfo(HttpSession session) {
        String door=(String) session.getAttribute("admissionTicket");
        return new ResponseData(door,true);
    }


    @ApiOperation(value = "考试情况记载表")
    @RequestMapping(value = "/detailsInfo", method = RequestMethod.GET)
    public ResponseData detailsInfo(HttpSession session) {
        String door=(String) session.getAttribute("details");
        return new ResponseData(door,true);
    }


    @ApiOperation(value = "生成考生座位对照表")
    @RequestMapping(value = "/contrastInfo", method = RequestMethod.GET)
    public ResponseData contrastInfo(HttpSession session) {
        String door=(String) session.getAttribute("contrast");
        return new ResponseData(door,true);
    }




}
