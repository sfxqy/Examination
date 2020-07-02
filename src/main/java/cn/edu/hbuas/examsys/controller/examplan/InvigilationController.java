package cn.edu.hbuas.examsys.controller.examplan;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Invigilation;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.service.examplan.InviService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 * 监考老师的管理
 */

@Api(tags = "监考老师管理：本次考试任务中监考老师的管理")
@RestController
@RequestMapping("/examplan")
public class InvigilationController {
    private static Logger logger = LoggerFactory.getLogger(InvigilationController.class);
    @Autowired
    private InviService inviService;

    @ApiOperation("批量添加监考老师，单个或多个")
    @ResponseBody
    @PostMapping("/edit/invigilations")
    public ResponseData addInvisToTaskPlan(@RequestBody List<Invigilation> invigilationList, HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        inviService.insertAnInviList(invigilationList,taskplan.getTid(),user);
        return new ResponseData("监考老师添加成功",true);
    }

    @ApiOperation("批量删除监考老师，单个或多个")
    @ResponseBody
    @DeleteMapping("/edit/invigilations")
    public ResponseData deleteInvisToTaskPlan(@RequestBody List<Invigilation> invigilationList,HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        inviService.deleteInviList(invigilationList,user.getCid(),taskplan.getTid());
        return new ResponseData("监考老师删除成功",true);
    }

    @ApiOperation("获取全部的监考老师，分页展示")
    @ResponseBody
    @GetMapping("/invigilations")
    public ResponseData selectInvisToTaskPlan(Integer pageNum,Integer pageSize,HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        PageInfo<Teacher> teacherPageInfo = inviService.selectAllInviByCid(taskplan.getTid(),user.getCid(),pageSize,pageNum);
        ResponseData responseData = new ResponseData("监考老师信息查询成功",true);
        responseData.setRows(teacherPageInfo.getList());
        responseData.setTotal(teacherPageInfo.getTotal());
        return responseData;
    }

}
