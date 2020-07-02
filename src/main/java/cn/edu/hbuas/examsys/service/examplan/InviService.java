package cn.edu.hbuas.examsys.service.examplan;

import cn.edu.hbuas.examsys.dao.InvigilationDao;
import cn.edu.hbuas.examsys.dao.TeacherDao;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Invigilation;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 * 监考老师相关服务
 */

@Service
public class InviService {
    @Autowired
    private InvigilationDao invigilationDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private ExamPlanMapper examPlanMapper;

    private static Logger logger = LoggerFactory.getLogger(InviService.class);

    /**
     * 添加一个监考老师
     * @param invigilation
     * @return
     */
    public int insertAnInvi(Invigilation invigilation, User user) throws Exception{
        Teacher teacher = teacherDao.selectTeacherByNumber(invigilation.getNumber());
        if(null == teacher){
            logger.info("查询不到教师信息");
            BusinessException businessException = new BusinessException("无效的教师编号");
            businessException.setData(invigilation);
            throw businessException;
        }
        if(teacher.getCid() != user.getCid()){
            if(invigilation.getCid() > 0){
                logger.info("查询的教师信息院别编号不一致");
                throw new BusinessException("教师数据不一致");
            }
        }
        return invigilationDao.insertInvi(invigilation);
    }


    /**
     * 批量添加监考老师
     * @param invigilationList
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    public int insertAnInviList(List<Invigilation> invigilationList,Integer tid,User user) throws Exception{
        int total = 0;
        for (Invigilation invigilation:invigilationList){
            invigilation.setTid(tid);
            invigilation.setCid(user.getCid());
            total += insertAnInvi(invigilation,user);
        }
        return total;
    }

    /**
     * 批量删除监考老师
     * @param cid 如果cid 为空，cid 不是筛选条件
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteInviList(List<Invigilation> invigilationList,Integer cid,Integer tid){
        for (Invigilation invigilation:invigilationList){
            if(cid > 0)
                invigilation.setCid(cid);
            invigilation.setTid(tid);
            invigilationDao.deleteInviByCondition(invigilation);
        }
        return invigilationList.size();
    }

    /**
     * 获取全部的监考老师信息
     * @param cid
     * @param tid
     * @return
     */
    public PageInfo<Teacher> selectAllInviByCid(Integer tid,Integer cid,Integer size,Integer num){
        Invigilation invigilation = new Invigilation();
        if(cid > 0)
            invigilation.setCid(cid);
        invigilation.setTid(tid);
        PageHelper.startPage(num,size);
        PageInfo<Teacher> teacherPageInfo = new PageInfo<>(examPlanMapper.selectInviTeacher(invigilation));
        return teacherPageInfo;
    }
}
