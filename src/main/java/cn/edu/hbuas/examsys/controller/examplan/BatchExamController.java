package cn.edu.hbuas.examsys.controller.examplan;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Batch;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.BatchService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 */
@RestController
@RequestMapping("/examplan")
@Api(tags = "同批考试管理：考试任务中同批考试的管理")
public class BatchExamController {
    @Autowired
    private BatchService batchService;

    @ApiOperation("添加一个同批考试科目")
    @PostMapping("/edit/batchs")
    @ResponseBody
    public ResponseData addAnBatchExam(Batch batch, HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        if(batch.getBname().contains("（")||batch.getBname().contains("）")||batch.getBname().contains(" "))
            throw new BusinessException("同批考试课程名称包含空格或中文括号");
        batchService.addBatch(batch,taskplan.getTid(),user.getCid());
        return new ResponseData("添加同批考试成功",true);
    }

    @ApiOperation("批量删除同批考试数据")
    @DeleteMapping("/edit/batchs")
    @ResponseBody
    public ResponseData deleteAnBatchExam(@RequestBody List<Batch> batchList, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        batchService.deleteBatchList(batchList,user.getCid(),taskplan.getTid());
        return new ResponseData("删除同批考试成功",true);
    }

    @ResponseBody
    @ApiOperation("分页获取同批考试数据")
    @GetMapping("/batchs")
    public ResponseData selectBatchList(HttpSession httpSession,Integer pageNum,Integer pageSize){
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan)httpSession.getAttribute("taskplan");
        Batch batch = new Batch();
        batch.setTid(taskplan.getTid());
        if(user.getCid() > 0)
            batch.setType(user.getCid()+"");
        PageInfo<Batch> batchPageInfo = batchService.selectBatchList(batch,pageNum,pageSize);
        ResponseData responseData = new ResponseData("同批考试数据获取成功",true);
        responseData.setTotal(batchPageInfo.getTotal());
        responseData.setRows(batchPageInfo.getList());
        return responseData;
    }
}
