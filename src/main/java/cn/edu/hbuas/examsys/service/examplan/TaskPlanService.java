package cn.edu.hbuas.examsys.service.examplan;

import cn.edu.hbuas.examsys.dao.*;
import cn.edu.hbuas.examsys.model.Constants;
import cn.edu.hbuas.examsys.model.ContextContainer;
import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.service.baseinfo.TeacherService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.DateTimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author SFX
 * 考试任务计划相关操作
 */
@Service
public class TaskPlanService {
    private static Logger logger = LoggerFactory.getLogger(TaskPlanService.class);

    @Autowired
    private ContextContainer contextContainer;

    @Autowired
    private TaskPlanDao taskPlanDao;      //考试任务
    @Autowired
    private ExamPlanMapper examPlanMapper;
    @Autowired
    private ExamCourseDao examCourseDao;     //考试科目
    @Autowired
    private ArrangeDao arrangeDao;           //考生信息
    @Autowired
    private InvigilationDao invigilationDao;    //监考老师
    @Autowired
    private SurfaceDao surfaceDao;             //编排数据
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private DetailsDao detailsDao;
    @Autowired
    private ExamUtilService examUtilService;
    @Autowired
    private ExamDateDao examDateDao;
    @Autowired
    private BatchDao batchDao;

    @Autowired
    private TeacherService teacherService;


    /**
     * 管理员添加一个新的考试任务
     * @param newtaskplan
     * @throws Exception 添加失败
     * @return 返回刚刚添加进去的考试任务
     */
    @Transactional(rollbackFor = Exception.class)
    public Taskplan addTaskPlan(Taskplan newtaskplan) throws Exception{
        logger.info("事务开始：添加考试任务");
        newtaskplan.setState("1");
        int n = taskPlanDao.addTask(newtaskplan);
        logger.info("考试任务添加成功:{}",n);
        Taskplan res = taskPlanDao.selectTaskPlanByID(newtaskplan.getTid());
        logger.info("事务结束：添加考试任务");
        return res;
    }

    public Taskplan selectTaskPlanByTid(Integer tid){
        return taskPlanDao.selectTaskPlanByID(tid);
    }

    /**
     * 按照查找条件查找批量考试任务
     * @param select 条件：开始，结束时间，任务描述，任务状态
     * @return 结果集合
     * @throws Exception 查找数据为空
     */
    public List<Taskplan> selectTaskPlanByCondition(Taskplan select) throws Exception{
        List<Taskplan> taskplanList = taskPlanDao.selectTaskPlanByCondition(select);
        if(taskplanList.size() == 0){
            logger.info("查找的数据为0...");
            throw new BusinessException("查找不到数据");
        }
        logger.info("查找成功，数据为：{}",taskplanList.size());
        return taskplanList;
    }

    /**
     * 分页查询：按照查找条件查找批量考试任务
     * @param select 条件：开始，结束时间，任务描述，任务状态
     * @return 结果集合
     * @throws Exception 查找数据为空
     */
    public PageInfo<Taskplan> selectPageTaskPlanByCondition(Integer num,Integer size,Taskplan select) throws Exception{
        PageHelper.startPage(num,size);
        List<Taskplan> taskplanList = taskPlanDao.selectTaskPlanByCondition(select);
        if(taskplanList.size() == 0){
            logger.info("查找的数据为0...");
            throw new BusinessException("查找不到数据");
        }
        PageInfo<Taskplan> pageInfo = new PageInfo<>(taskplanList);
        return pageInfo;
    }

    /**
     * 根据考试任务编号删除一次考试任务的编排结果信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePlanResult(HttpSession httpSession, Taskplan taskplan) throws Exception{
        taskplan.setState(""+Constants.PLAN_EDIT);
        taskPlanDao.updateTaskPlanById(taskplan);
        httpSession.setAttribute("taskplan",taskplan);
        surfaceDao.deleteByTid(taskplan.getTid());
        Details details = new Details();
        details.setTid(taskplan.getTid());
        detailsDao.deleteDetailsesByCondition(details);
    }

    /**
     * 根据主键完全删除一个考试任务
     * @param taskplan
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTaskPlan(Taskplan taskplan) throws Exception{
        taskplan = taskPlanDao.selectTaskPlanByID(taskplan.getTid());
        Integer state = Integer.parseInt(taskplan.getState());
        if(state >= Constants.PLAN_PROCEED)
            throw new BusinessException("考试任务不可删除");
        logger.info("事务开始：删除考试任务");
        if(taskPlanDao.deleteTask(taskplan) < 1){
            logger.info("删除考试任务失败，删除0个...");
            throw new BusinessException("删除考试任务失败");
        }
        logger.info("删除考试任务成功...");
        int sum;
        if(taskplan.getType().equals(Constants.REGULAR_EXAMINATION+"")){
           sum = examCourseDao.deleteExamCourseByTid(taskplan.getTid());
           logger.info("(常规考试)级联删除考试科目信息成功：{}...",sum);

            sum = arrangeDao.deleteStudentByTid(taskplan.getTid());
            logger.info("(常规考试)级联删除考生信息成功：{}...",sum);

            sum = invigilationDao.deleteInvigilationByTid(taskplan.getTid());
            logger.info("(常规考试)级联删除监考老师信息成功：{}...",sum);
        }
        sum = surfaceDao.deleteByTid(taskplan.getTid());
        logger.info("级联删除编排数据成功：{}...",sum);

        Details details = new Details();
        details.setTid(taskplan.getTid());
        sum = detailsDao.deleteDetailsesByCondition(details);
        logger.info("删除考生考试详情数据：{}",sum);

        Batch batch = new Batch();
        batch.setTid(taskplan.getTid());
        sum = batchDao.deleteDetailsByCondition(batch);
        logger.info("删除同批考试数据：{}",sum);

        Examdate examdate = new Examdate();
        examdate.setTid(taskplan.getTid());
        examDateDao.deleteExamTimeByCondition(examdate);
        //删除考试时间映射数据

        Room room = new Room();
        room.setTid(taskplan.getTid());
        sum = roomDao.deleteRoomByCondition(room);
        logger.info("删除考场映射数据：{}",sum);

        logger.info("事务结束：删除考试任务");
    }

    /**
     * 编排一个具体的考试科目
     * @param taskplan
     * @param chooseExam
     * @param allRoom
     * @param examDate
     * @param timeIndex
     * @return 返回编排情况 0：编排成功  1：缺少教室  2：缺少监考老师
     */
    private int planAnExam(Taskplan taskplan,Examcourse chooseExam,List<Examcourse> allExams,List<Teacher> allInviTeacher, List<Room> allRoom,Date examDate,int timeIndex) throws Exception{
        //查找考试时间列表
        Examdate examDatecon = new Examdate();
        examDatecon.setTid(taskplan.getTid());
        List<Examdate> timeList = examDateDao.selectExamTimeByCondition(examDatecon);     //考试时间列表

        //查找本场考试的全部考生
        List<Examinee> examineeList = arrangeDao.selectExamineeListByTidEidByRand(taskplan.getTid(),chooseExam.getEid());

        int needRoom = examUtilService.countNeedRoom(chooseExam);
        if(needRoom > allRoom.size()){
            if(allRoom.size() > 0){
                logger.error("当前教室：{}",allRoom.get(0).getPlace());
            }else {
                logger.error("使用完全部教室...");
            }
            return 1;
        }

        //查找监考教师
        List<Teacher> teacherList = examUtilService.chooseNumInviTeacher(allInviTeacher,chooseExam.getCid(),needRoom*2);

        Surface surface;
        Room chooseRoom;
        int studentNum;
        Teacher teacher1;
        Teacher teacher2;

        List<List<Examinee>> cutRoom = examUtilService.getStudentRoomList(examineeList);
        for (List<Examinee> stuList:cutRoom){
            surface = new Surface();
            chooseRoom = allRoom.remove(0);
            surface.setTid(taskplan.getTid());
            surface.setEid(chooseExam.getEid());
            surface.setTesttime(examDate);
            surface.setBegin(timeList.get((timeIndex-1)*2).getEtime());
            surface.setEnd(timeList.get((timeIndex-1)*2+1).getEtime());
            surface.setRid(chooseRoom.getRid());
            surface.setNumber(stuList.size());
            if((teacherList != null)&&(teacherList.size() >= 2)) {
                teacher1 = teacherList.remove(0);
                teacher2 = teacherList.remove(0);
                teacher1.setTimes(teacher1.getTimes()+1);
                teacher2.setTimes(teacher2.getTimes()+1);
                allInviTeacher.remove(teacher1);
                allInviTeacher.remove(teacher2);
                surface.setTeacher(teacher1.getTname()+"-"+teacher2.getTname());
                surface.setWorknumber(teacher1.getNumber()+"-"+teacher2.getNumber());
            }
            surfaceDao.insertOneSurface(surface);
            studentNum = 1;
            for (Examinee arrange:stuList){
                Details details = new Details();
                details.setSuid(surface.getSuid());
                details.setSid(arrange.getSid());
                details.setSname(arrange.getSname());
                details.setSex(arrange.getSex());
                details.setPname(arrange.getPname());
                details.setTid(taskplan.getTid());
                details.setName(chooseExam.getName());
                details.setTid(taskplan.getTid());
                details.setEid(chooseExam.getEid());
                details.setExamtime(examDate);
                details.setBegin(timeList.get((timeIndex-1)*2).getEtime());
                details.setEnd(timeList.get((timeIndex-1)*2+1).getEtime());
                details.setPlace(chooseRoom.getPlace());
                details.setSeat(""+studentNum);
                details.setRid(chooseRoom.getRid());
                detailsDao.insertDetails(details);
                studentNum++;
            }
        }
        allExams.remove(chooseExam);
        return 0;
    }

    /**
     * 按照院别编排校级同批考试
     * @param taskplan
     * @param allRoom
     * @param examDate
     * @param timeIndex
     * @return 返回编排情况 0：编排成功  1：缺少教室  2：缺少监考老师
     */
    private int planAnSchoolExam(Taskplan taskplan,HashMap<String,List<Examcourse>> stringListHashMap,List<Examcourse> allExams,List<Teacher> allInviTeacher, List<Room> allRoom,Date examDate,int timeIndex) throws Exception{
        Examdate examDatecon = new Examdate();
        examDatecon.setTid(taskplan.getTid());
        List<Examdate> timeList = examDateDao.selectExamTimeByCondition(examDatecon);     //考试时间列表
        List<Teacher> teacherList;
        Iterator<Map.Entry<String,List<Examcourse>>> bathExamIter = stringListHashMap.entrySet().iterator();
        Surface surface;
        Room chooseRoom;
        int start_room_size = allRoom.size();
        int studentNum;
        Teacher teacher1 = null;
        Teacher teacher2 = null;
        while (bathExamIter.hasNext()){
            Map.Entry<String,List<Examcourse>> examListEntry = bathExamIter.next();
            //查找本院考试的全部考生
            HashMap<Integer,List<Examinee>> examineeHashMap = examUtilService.getCollegeStudent(taskplan.getTid(),examListEntry.getValue());
            Iterator<Map.Entry<Integer,List<Examinee>>> examineeEntryIterator = examineeHashMap.entrySet().iterator();
            //编排各个院
            while (examineeEntryIterator.hasNext()){
                Map.Entry<Integer,List<Examinee>> examEntry = examineeEntryIterator.next();
                Integer cid = examEntry.getKey();
                List<Examinee> collegeStuList = examEntry.getValue();
                List<List<Examinee>> cutRoom = examUtilService.getStudentRoomList(collegeStuList);
                teacherList = examUtilService.chooseNumInviTeacher(allInviTeacher,cid,cutRoom.size()*2);
                for (List<Examinee> stuList:cutRoom){
                    surface = new Surface();
                    chooseRoom = allRoom.remove(0);
                    surface.setTid(taskplan.getTid());
                    surface.setEid(stuList.get(0).getEid());
                    surface.setTesttime(examDate);
                    surface.setBegin(timeList.get((timeIndex-1)*2).getEtime());
                    surface.setEnd(timeList.get((timeIndex-1)*2+1).getEtime());
                    surface.setRid(chooseRoom.getRid());
                    surface.setNumber(stuList.size());
                    if(teacherList.size() >= 2){
                        teacher1 = teacherList.remove(0);
                        teacher2 = teacherList.remove(0);
                        teacher1.setTimes(teacher1.getTimes()+1);
                        teacher2.setTimes(teacher2.getTimes()+1);
                        allInviTeacher.remove(teacher1);
                        allInviTeacher.remove(teacher2);
                        surface.setTeacher(teacher1.getTname()+"-"+teacher2.getTname());
                        surface.setWorknumber(teacher1.getNumber()+"-"+teacher2.getNumber());
                    }
                    surfaceDao.insertOneSurface(surface);
                    studentNum = 1;
                    for (Examinee arrange:stuList){
                        Details details = new Details();
                        details.setSuid(surface.getSuid());
                        details.setSid(arrange.getSid());
                        details.setSname(arrange.getSname());
                        details.setSex(arrange.getSex());
                        details.setPname(arrange.getPname());
                        details.setTid(taskplan.getTid());
                        details.setName(examListEntry.getValue().get(0).getName());    //更改
                        details.setTid(taskplan.getTid());
                        details.setEid(arrange.getEid());
                        details.setExamtime(examDate);
                        details.setBegin(timeList.get((timeIndex-1)*2).getEtime());
                        details.setEnd(timeList.get((timeIndex-1)*2+1).getEtime());
                        details.setPlace(chooseRoom.getPlace());
                        details.setSeat(""+studentNum);
                        details.setRid(chooseRoom.getRid());
                        detailsDao.insertDetails(details);
                        studentNum++;
                    }
                }
            }
        }
        logger.info("开始教室:{}  剩余教室:{}",start_room_size,allRoom.size());
        bathExamIter = stringListHashMap.entrySet().iterator();
        while (bathExamIter.hasNext()){
            for (Examcourse examcourse:bathExamIter.next().getValue()){
                allExams.remove(examcourse);
                logger.info("删除:{} 班级：{} 剩余:{} ",examcourse.getName(),examcourse.getClazz(),allExams.size());
            }
        }
        return 0;
    }


    /**
     * 编排院级同批考试
     * @param grade 当前编排年级
     * @param examDate 考试日期
     * @param timeIndex 考试时间索引
     * @param taskplan 考试任务
     * @param allBatchs 所有的同批考试数据
     * @param allExams 所有的考试科目
     * @param allRooms 所有的考试教室
     * @param allInviTeacher 所有的监考老师
     * @return 编排成功返回0，缺少教室返回1
     */
    public int planBatchExam(HttpSession httpSession,String grade, Date examDate,Integer timeIndex,Taskplan taskplan,List<Batch> allBatchs,List<Examcourse> allExams, List<Room> allRooms, List<Teacher> allInviTeacher,HashMap<String,Integer> clazzNameMap,StringBuffer stringBuffer) throws Exception{
        List<Batch> collegeBatchList = examUtilService.getCollegeBatchList(allBatchs,grade);
        List<Examcourse> batchExamsList;
        int needRoom;
        int batchPlabn = 0;
        logger.info("剩余同批考试数量：{}",allBatchs.size());
        for (Batch collegeBatch:collegeBatchList){
            batchExamsList = examUtilService.chooseBatchExam(allExams,collegeBatch.getBname(),clazzNameMap,stringBuffer);
            if(batchExamsList == null){
                logger.warn("院级同批考试出现已经考试过的班级：{}",collegeBatch.getBname());
                continue;
            }
            if(batchExamsList.size() == 0){
                logger.error("发现无效的同批考试：{}",collegeBatch.getBname());
                allBatchs.remove(collegeBatch);
                httpSession.setAttribute("planMsg","发现无效的同批考试："+collegeBatch.getBname());
                httpSession.setAttribute("planStatus",true);
                continue;
            }
            needRoom = examUtilService.countNeedRoom(batchExamsList);
            if(allRooms.size() < needRoom){
                logger.error("院级同批考试教室不足:{}  {}",collegeBatch.getBname(),allRooms.get(0).getPlace());
                return 1;
            }
            batchPlabn++;
            logger.warn("编排院级同批考试:");
            for (Examcourse examcourse:batchExamsList){
                clazzNameMap.put(examcourse.getClazz(),1);
                logger.warn("院级同批： 考试名称：{} 考试班级：{} 人数：{}  时间索引：{}",examcourse.getName(),examcourse.getClazz(),examcourse.getTotal(),timeIndex);
                planAnExam(taskplan,examcourse,allExams,allInviTeacher,allRooms,examDate,timeIndex);
            }
            allBatchs.remove(collegeBatch);
        }
        logger.warn("本次编排同批数量：{}",batchPlabn);
        return 0;
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void startRegularExamPlan(Taskplan taskplan,HttpSession httpSession) throws Exception{
        try {
            logger.info("进行常规考试任务编排");
            StartPlan(taskplan,httpSession);
        }catch (BusinessException bue){
            logger.error("捕获编排任务时的异常");
            httpSession.setAttribute("planMsg",bue.getMessage());
            httpSession.setAttribute("planStatus",false);
            throw bue;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            httpSession.setAttribute("planMsg","编排时出现未知错误："+e.getMessage());
            httpSession.setAttribute("planStatus",false);
            throw e;
        }
    }

    /**
     * 开始进行编排
     * @param taskplan 需要编排的考试任务
     */
    private void StartPlan(Taskplan taskplan ,HttpSession httpSession)  throws Exception{
        logger.warn("事务开始：考试编排...");
        StringBuffer stringBuffer = new StringBuffer();
        //查询年级列表
        List<String> gradesList = taskPlanDao.selectTestGradeList();
        if(gradesList.size() < 1){
            httpSession.setAttribute("planMsg","考试科目未上传");
            httpSession.setAttribute("planStatus",false);
            logger.error("查询的年级列表为空...");
            throw new BusinessException("考试科目未上传");
        }

        logger.info("年级列表查找成功：{}", Arrays.toString(gradesList.toArray()));

        //根据考试任务编号查询所有的考试科目
        Examcourse select = new Examcourse();
        select.setTotal(5);                               //筛选条件，大于0人的考试科目
        select.setTid(taskplan.getTid());
        List<Examcourse> allExamCourseList = examCourseDao.selectExamByTidCidPidGradeClassName(select);
        if(allExamCourseList.size() == 0){
            logger.error("考试科目为空...");
            httpSession.setAttribute("planMsg","考试科目表未上传");
            httpSession.setAttribute("planStatus",false);
            throw new BusinessException("考试科目未上传");
        }
        logger.info("查询考试科目成功：{}",allExamCourseList.size());

        //查找考试时间列表
        Examdate examdate = new Examdate();
        examdate.setTid(taskplan.getTid());
        List<Examdate> timeList = examDateDao.selectExamTimeByCondition(examdate);     //考试时间列表
        if(timeList.size() == 0){
            httpSession.setAttribute("planMsg","考试时间映射表未上传");
            httpSession.setAttribute("planStatus",false);
            logger.error("考试时间映射表未上传...");
            throw new BusinessException("考试时间映射表未上传");
        }
        logger.info("考试时间映射表查询成功：{}",timeList.size());

        Date examDate = taskplan.getBegin();                  //开始考试日期

        List<Examcourse> gradeExamCouse;                      //年级的考试科目
        HashMap<String,Integer> classMarks = new HashMap<String, Integer>(); //记录已经编排的班级
        //全部监考教师信息
        Invigilation conditionInvi = new Invigilation();
        conditionInvi.setTid(taskplan.getTid());
        List<Teacher> allTeacherList = invigilationDao.selectInviTeacherInfo(conditionInvi);
        logger.info("监考老师查询成功:{}",allTeacherList.size());
        List<Teacher> todayTeacherList =  examUtilService.sortAndCopyTeactherList(allTeacherList);
        List<Teacher> thisTimeTeacherList;


        Room room = new Room();
        room.setTid(taskplan.getTid());
        List<Room> allRoom = roomDao.selectRoomByCondition(room);        //所有的考试教室
        if(allRoom.size() == 0){
            httpSession.setAttribute("planMsg","考试教室表未上传");
            httpSession.setAttribute("planStatus",false);
            logger.error("考试教室为空...");
            throw new BusinessException("考试教室表为空...");
        }
        logger.info("考试教室映射表查询成功:{}",allRoom.size());
        String grade;                                         //当前编排的年级
        int allTime = timeList.size()/2;                      //一天的考试场数
        int timeIndex = 1;                                    //当前是第几场考试
        Iterator<String> stringIterable;     //编排的年级列表

        httpSession.setAttribute("planMsg","数据验证完成...");
        httpSession.setAttribute("planStatus",true);

        Batch batch = new Batch();
        batch.setTid(taskplan.getTid());
        List<Batch> allBatchs = batchDao.selectDetailsByCondition(batch);
        List<Batch> schoolBatchs = examUtilService.getSchoolBatchList(allBatchs);
        Batch chooseBatch;
        List<Examcourse> batchExamsList;                               //一个同批考试的所有科目
        logger.info("同批考试数据查询成功:{}",allBatchs.size());

        logger.warn("开始考试编排...");
        int planResult ;
        HashMap<String,Integer> batchNameStr = examUtilService.getAllBatchNameMap(allBatchs);

        httpSession.setAttribute("planMsg","开始编排校级同批考试...");
        httpSession.setAttribute("planStatus",true);
        while (schoolBatchs.size() > 0){
            logger.warn("校级同批考试编排中,");
            stringIterable = gradesList.iterator();
            while (stringIterable.hasNext()){
                //处理时间和考场次数
                if(timeIndex > allTime){
                    todayTeacherList = examUtilService.sortAndCopyTeactherList(allTeacherList);
                    timeIndex = 1;
                    examDate = DateTimeUtil.dateAddDay(examDate,1);
                }
                grade = stringIterable.next();
                //选择一个同批考试
                logger.warn("编排计划信息：剩余科目：{}  年级：{}  日期：{}  时间：{}",allExamCourseList.size(),grade,examDate,timeList.get(timeIndex).getEtime());
                if(allBatchs.size() <= 0){
                    break;
                }
                chooseBatch = examUtilService.getSchoolBatch(schoolBatchs,grade);
                //如果为NULL，则当前年级同批考试编排完毕
                if(chooseBatch == null){
                    stringIterable.remove();
                    continue;
                }
                //获取所有的考试教室
                room = new Room();
                room.setTid(taskplan.getTid());
                allRoom = roomDao.selectRoomByCondition(room);

                thisTimeTeacherList = examUtilService.copyTeactherList(todayTeacherList);


                //同批考试的所有考试
                HashMap<String,List<Examcourse>> multiBatchCourse = examUtilService.chooseMultiBatchExam(allExamCourseList,chooseBatch.getBname(),stringBuffer);
                Iterator<Map.Entry<String,List<Examcourse>>> bathExamIter = multiBatchCourse.entrySet().iterator();
                int needRoom = 0;
                int elseExamCourse = 0;   //剩余考试科目
                while (bathExamIter.hasNext()){
                    batchExamsList = bathExamIter.next().getValue();
                    elseExamCourse += batchExamsList.size();
                    needRoom += examUtilService.countNeedRoom(batchExamsList);
                }
                logger.warn("编排计划信息：本次科目名称：{}  本次科目数量：{}  本次剩余教室：{}",chooseBatch.getBname(),elseExamCourse,allRoom.size());
                if(elseExamCourse <= 0){
                    schoolBatchs.remove(chooseBatch);
                    allBatchs.remove(chooseBatch);
                    continue;
                }
                httpSession.setAttribute("planMsg","校级同批："+chooseBatch.getBname()+"  编排中...");
                httpSession.setAttribute("planStatus",true);
                //编排校级同批考试
                planResult = planAnSchoolExam(taskplan,multiBatchCourse,allExamCourseList,thisTimeTeacherList,allRoom,examDate,timeIndex);
                if(planResult > 1){
                    httpSession.setAttribute("planMsg","校级同批考试教室不足");
                    httpSession.setAttribute("planStatus",false);
                    logger.error("缺少监考教室");
                    throw new BusinessException("校级同批考试编排异常，缺少监考教室");
                }
                allBatchs.remove(chooseBatch);
                schoolBatchs.remove(chooseBatch);
                timeIndex++;
            }
            if(schoolBatchs.size() <= 0){
                break;
            }
        }
        httpSession.setAttribute("planMsg","校级同批考试编排完成...");
        httpSession.setAttribute("planStatus",true);

        httpSession.setAttribute("planMsg","开始编排院级同批考试和常规考试...");
        httpSession.setAttribute("planStatus",true);

        logger.warn("校级同批考试完成，其他考试开始...");
        gradesList = taskPlanDao.selectTestGradeList();
        todayTeacherList = examUtilService.sortAndCopyTeactherList(allTeacherList);
        while (allExamCourseList.size() > 0){
            stringIterable = gradesList.iterator();
            while (stringIterable.hasNext()){
                //一个半天
                grade = stringIterable.next();
                classMarks.clear();
                //处理时间和考场次数
                if(timeIndex > allTime){
                    todayTeacherList = examUtilService.sortAndCopyTeactherList(allTeacherList);
                    timeIndex = 1;
                    examDate = DateTimeUtil.dateAddDay(examDate,1);
                }
                logger.warn("编排计划信息：年级：{}  日期：{}  时间：{}  剩余科目：{}",grade,examDate,timeIndex,allExamCourseList.size());
                room = new Room();
                room.setTid(taskplan.getTid());
                allRoom = roomDao.selectRoomByCondition(room);
                thisTimeTeacherList = examUtilService.copyTeactherList(todayTeacherList);
                //院级同批考试编排
                if(allBatchs.size() > 0){
                    planResult = planBatchExam(httpSession,grade,examDate,timeIndex,taskplan,allBatchs,allExamCourseList,allRoom,thisTimeTeacherList,classMarks,stringBuffer);
                    if(planResult == 1){
                        logger.warn("院级同批缺少教室退出...");
                        timeIndex++;
                        classMarks.clear();
                        continue;
                    }
                }

                //编排本年级常规考试科目
                gradeExamCouse = examUtilService.chooseGradeCourse(allExamCourseList,batchNameStr,grade,classMarks);
                if(gradeExamCouse.size() == 0){
                    stringIterable.remove();
                    if(allExamCourseList.size() == 0)
                        break;
                    continue;
                }
                logger.warn("编排常规考试科目中...");
                for (Examcourse examcourse:gradeExamCouse){
                    planResult = planAnExam(taskplan,examcourse,allExamCourseList,thisTimeTeacherList,allRoom,examDate,timeIndex);
                    if(planResult != 0){
                        if(planResult == 1){
                            logger.error("考试编排缺少考试教室：  院系编号:{} 考试科目:{} 考试班级:{}",examcourse.getCid(),examcourse.getName(),examcourse.getClazz());
                            break;
                        }
                    }
                    logger.info("编排科目信息：名称：{} 班级：{}  人数：{}",examcourse.getName(),
                            examcourse.getClazz(),examcourse.getTotal());
                }
                httpSession.setAttribute("planMsg","剩余同批数量："+allBatchs.size()+"  剩余总科目数量："+allExamCourseList.size());
                httpSession.setAttribute("planStatus",true);
                timeIndex++;
                classMarks.clear();
            }
            if(allExamCourseList.size() <= 0){
                break;
            }
        }
        httpSession.setAttribute("planMsg",stringBuffer.toString());
        httpSession.setAttribute("planStatus",true);
        logger.info(stringBuffer.toString());
        for (Teacher teacher:allTeacherList){
            teacherService.updateTeacherInfo(teacher);
        }
        logger.info("编排成功...");
        taskplan.setState("2");
        taskplan.setEnd(examDate);
        taskPlanDao.updateTaskPlanById(taskplan);
        httpSession.setAttribute("taskplan",taskplan);
        httpSession.setAttribute("planMsg","本次考试任务编排完成");
        httpSession.setAttribute("planStatus",true);
    }


}
