package cn.edu.hbuas.examsys.service.examplan;

import cn.edu.hbuas.examsys.dao.BatchDao;
import cn.edu.hbuas.examsys.dao.ExamCourseDao;
import cn.edu.hbuas.examsys.mybatis.pojo.Batch;
import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author SFX
 * 同批考试数据管理
 */
@Service
public class BatchService {
    @Autowired
    private BatchDao batchDao;
    @Autowired
    private ExamCourseDao examCourseDao;

    /**
     * 添加一个同批考试数据
     * @param batch 同批数据
     * @param tid 考试任务编号
     * @param role 角色编号
     * @return
     */
    public int addBatch(Batch batch,Integer tid,Integer role) throws Exception{
        if(role > 0)
            batch.setType(role+"");
        else
            batch.setType("0");
        batch.setTid(tid);
        String batchName = batch.getBname();
        batchName = batchName.replace(" ","");
        batch.setBname(batchName);
        String conflict = batchCourseConflictDetection(tid,batchName);
        if(conflict != null)
            throw new BusinessException("存在冲突班级:"+conflict);
        return batchDao.insertBatch(batch);
    }

    /**
     * 根据主键批量删除同批考试
     * @param batches
     * @return
     */
    public int deleteBatchList(List<Batch> batches,Integer role,Integer tid){
        int total = 0;
        for (Batch batch:batches){
            batch.setTid(tid);
            if(role > 0)
                batch.setType(role+"");
            total += batchDao.deleteDetailsByCondition(batch);
        }
        return total;
    }


    public PageInfo<Batch> selectBatchList(Batch batch,Integer pageNum,Integer pafeSize){
        PageHelper.startPage(pageNum,pafeSize);
        PageInfo<Batch> pageInfo = new PageInfo<>(batchDao.selectDetailsByCondition(batch));
        return pageInfo;
    }


    /**
     * 对进行同批考试的科目进行班级冲突检测
     * @param batchName 同批科目名称
     * @return 返回 null 表示不存在冲突班级 , 否则返回冲突的班级
     */
    public String batchCourseConflictDetection(Integer tid,String batchName){
        String[] examNames = batchName.split("-");
        Examcourse select = new Examcourse();
        select.setTid(tid);
        List<Examcourse> examcourseList = new ArrayList<>();
        for (String examName:examNames){
            select.setName(examName);
            examcourseList.addAll(examCourseDao.selectExamByTidCidPidGradeClassName(select));
        }
        if(examcourseList.size() < 1)
            return null;
        HashMap<String,String> integerHashMap = new HashMap<>();
        for (Examcourse examcourse:examcourseList){
            if(integerHashMap.containsKey(examcourse.getClazz()))
                return examcourse.getClazz()+":"+examcourse.getName()+"-"+integerHashMap.get(examcourse.getClazz());
            else
                integerHashMap.put(examcourse.getClazz(),examcourse.getName());
        }
        return null;
    }
}
