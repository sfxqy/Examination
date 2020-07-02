package cn.edu.hbuas.examsys.springmvc.scheduled;

import cn.edu.hbuas.examsys.service.examplan.ExamUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author SFX
 */
@Component
public class TaskPlanSchedule {

    private static final Logger logger = LoggerFactory.getLogger(TaskPlanSchedule.class);
    @Autowired
    private ExamUtilService examUtilService;

    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(fixedRate = 20000)
    public void updateExamTaskState(){
        logger.info("定时任务：更新考试任务状态");
        examUtilService.updateExamPlanState();
    }
}
