package cn.edu.hbuas.examsys.controller.examplan;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Examdate;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.ExamTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 * 考试时间数据的处理
 */
@Controller
@Api(tags = "对于考试时间的处理")
@RequestMapping("/examplan/edit")
public class ExamTimeController {
    @Autowired
    private ExamTimeService examTimeService;

    @ResponseBody
    @ApiOperation("批量添加考试时间，单个或批量添加")
    @PostMapping("/examtime")
    public ResponseData addExamTime(@RequestBody List<Examdate> examtimes, HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan)httpSession.getAttribute("taskplan");
        if(user.getCid() > 0)
            return new ResponseData("用户角色错误",false);
        examTimeService.insertExamTimeList(examtimes,taskplan.getTid());
        ResponseData responseData = new ResponseData("考试时间添加成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("根据考试任务编号查询全部的考试时间")
    @GetMapping("/examtimes")
    public ResponseData selectAllExamTimeByTID(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan)httpSession.getAttribute("taskplan");
        if(user.getCid() > 0)
            return new ResponseData("用户角色错误",false);
        List<Examdate> examdates = examTimeService.selectAllExamTimes(taskplan.getTid());
        ResponseData responseData;
        responseData = new ResponseData("数据查找成功",true);
        responseData.setData(examdates);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("根据考试任务编号删除全部的考试时间")
    @DeleteMapping("/examtimes")
    public ResponseData deleteAllExamTimeByTID(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan)httpSession.getAttribute("taskplan");
        if(user.getCid() > 0)
            return new ResponseData("用户角色错误",false);
        examTimeService.deleteAllExamTimes(taskplan.getTid());
        ResponseData responseData = new ResponseData("考试时间删除成功",true);
        return responseData;
    }
}
