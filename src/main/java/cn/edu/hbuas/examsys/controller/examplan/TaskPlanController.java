package cn.edu.hbuas.examsys.controller.examplan;

import cn.edu.hbuas.examsys.dao.TaskPlanDao;
import cn.edu.hbuas.examsys.model.Constants;
import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.DegreeService;
import cn.edu.hbuas.examsys.service.examplan.ExamUtilService;
import cn.edu.hbuas.examsys.service.examplan.TaskPlanService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author SFX
 * 考试任务控制器
 */

@RestController
@Api(tags = "考试任务控制器，负责考试任务的增删改查")
@RequestMapping("/examplan")
public class TaskPlanController {
    private static Logger logger = LoggerFactory.getLogger(TaskPlanController.class);

    @Autowired
    private TaskPlanService taskPlanService;

    @Autowired
    private ExamUtilService examUtilService;
    @Autowired
    private TaskPlanDao taskPlanDao;
    @Autowired
    private DegreeService degreeService;

    @PostMapping("/taskplan")
    @ApiOperation("管理员添加考试任务计划")
    @ResponseBody
    public ResponseData insertTaskPlan(Taskplan taskplan,HttpSession session) throws Exception{
        User user = (User)session.getAttribute("user");
        taskplan.setCid("0");
        if(user.getCid() >= 0)
            return new ResponseData("用户角色错误",false);
        Taskplan success = taskPlanService.addTaskPlan(taskplan);
        logger.info("考试任务主键:{}",taskplan.getTid());
        ResponseData responseData = new ResponseData("添加考试任务成功",true);
        responseData.setData(success);
        return responseData;
    }

    @DeleteMapping("/edit/taskplan")
    @ApiOperation("管理员删除考试任务计划")
    @ResponseBody
    public ResponseData deleteTaskPlan(Taskplan taskplan,HttpSession session) throws Exception{
        User user = (User)session.getAttribute("user");
        if(user.getCid() >= 0)
            return new ResponseData("用户角色错误",false);
        taskPlanService.deleteTaskPlan(taskplan);
        return new ResponseData("删除考试任务成功",true);
    }

    @GetMapping("/taskplans")
    @ApiOperation("管理员或者二级院校获取所有的考试任务")
    @ResponseBody
    public ResponseData getAllTaskPlan(Integer pageNum, Integer pageSize) throws Exception{
        //设置查询条件
        Taskplan taskplan = new Taskplan();
        if((null ==pageNum)||(null == pageSize)){
            pageNum = 0;
            pageSize = 10;
        }
        PageInfo<Taskplan> pageInfo = taskPlanService.selectPageTaskPlanByCondition(pageNum,pageSize,taskplan);
        ResponseData responseData = new ResponseData("查找成功",true);
        responseData.setRows(pageInfo.getList());
        responseData.setTotal(pageInfo.getTotal());
        return responseData;
    }

    @GetMapping("/planning")
    @ApiOperation("开始进行考试编排")
    @ResponseBody
    public ResponseData startPlan(HttpSession session)  throws Exception{
        User user = (User)session.getAttribute("user");
        if(user.getCid() >= 0){
            throw new BusinessException("角色数据错误");
        }
        Taskplan taskplan = (Taskplan) session.getAttribute("taskplan");
        taskplan = taskPlanDao.selectTaskPlanByID(taskplan.getTid());
        if(null == taskplan){
            throw new BusinessException("考试任务信息出错");
        }
        Integer state = Integer.parseInt(taskplan.getState());
        if(state > Constants.PLAN_EDIT){
            logger.info("考试任务已经编排完成");
            throw new BusinessException("本次考试已经编排完成");
        }
        //本次考试任务为常规考试
        if (taskplan.getType().equals(Constants.REGULAR_EXAMINATION)){
            //常规考试
            taskPlanService.startRegularExamPlan(taskplan,session);
        }else if (taskplan.getType().equals(Constants.DEGREE_EXAMINATION)){
            try {
                logger.info("进行学位考试任务编排");
                    //学位考试编排
                    degreeService.planTest(taskplan,session);

            }catch (Exception e){
                throw new BusinessException("学位考试编排发生错误");
            }
        }
        session.setAttribute("planMsg","后台正在进行编排...");
        session.setAttribute("planStatus",true);
        return new ResponseData("考试任务正在后台编排中",true);
    }

    @DeleteMapping("/edit/planResult")
    @ApiOperation("删除本次考试任务的编排结果")
    @ResponseBody
    public ResponseData deletePlanResult(HttpSession httpSession) throws Exception{
        Taskplan taskplan = (Taskplan)httpSession.getAttribute("taskplan");
        Integer state = Integer.parseInt(taskplan.getState());
        User user = (User) httpSession.getAttribute("user");
        if(user.getCid() >= 0)
            throw new BusinessException("角色信息错误");
        if(state > Constants.PLAN_SUCCESS)
            throw new BusinessException("无法删除");
        taskPlanService.deletePlanResult(httpSession,taskplan);
        return new ResponseData("编排结果删除成功",true);
    }

    @ResponseBody
    @GetMapping("/notify")
    @ApiOperation("查询最新的编排信息")
    public ResponseData planNotifyInfo(HttpSession httpSession){
        String msg = (String)httpSession.getAttribute("planMsg");
        ResponseData responseData = new ResponseData("信息",true);
        Boolean state = (Boolean)httpSession.getAttribute("planStatus");
        if(null != msg){
            if((false == state)||(msg.equals("本次考试任务编排完成"))){
                httpSession.removeAttribute("planMsg");
                httpSession.removeAttribute("planStatus");
            }
            responseData.setStatus(state);
            responseData.setMsg(msg);
        }else {
            responseData.setStatus(false);
            responseData.setMsg("参数错误");
        }
        return responseData;
    }
}
