package cn.edu.hbuas.examsys.service.examplan;


import cn.edu.hbuas.examsys.dao.*;
import cn.edu.hbuas.examsys.mybatis.mapper.*;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.DateTimeUtil;
import cn.edu.hbuas.examsys.utils.FileUtil;
import cn.edu.hbuas.examsys.utils.ReportUtil;
import cn.hutool.core.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @author SFX
 * 学位考试处理
 */
@Service
public class DegreeService {
    private static Logger logger = LoggerFactory.getLogger(DegreeService.class);

    /**
     * 英语每间教室的容量
     */
    static final Integer ROOM_CAPACITY = 30;
    //英语学位考试
    static final String ENGLISH_DEGREE = "英语";
    //计算机学位考试
    static final String COMPUTE_DEGREE = "计算机";

    @Autowired
    TaskplanMapper taskplanMapper;
    @Autowired
    DetailsMapper detailsMapper;
    @Autowired
    private  TaskPlanDao taskPlanDao;

    @Autowired
    private ProfessionMapper professionMapper;
    @Autowired
    private DegreeMapper degreeMapper;

    @Autowired
    private InvigilationDao invigilationDao;

    @Autowired
    private ExamDateDao examDateDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ExamPlanMapper examPlanMapper;

    @Autowired
    private SurfaceDao surfaceDao;
    /**
     * 编排学位考试
     * @param taskPlan
     * @param httpSession
     * @return  0：编排成功
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void planTest(Taskplan taskPlan, HttpSession httpSession) throws BusinessException {
        DegreeExample degreeExample = new DegreeExample();
        DegreeExample.Criteria criteria = degreeExample.createCriteria();
        criteria.andTidEqualTo(taskPlan.getTid());
        //选取本次所有的所有考生
        List<Degree> degrees = degreeMapper.selectByExample(degreeExample);
        //计算考试 教室老师等条件是否足够是否足够
        //查询监考老师
        Invigilation conditionInvi = new Invigilation();
        conditionInvi.setTid(taskPlan.getTid());
        List<Teacher> allTeacherList = invigilationDao.selectInviTeacherInfo(conditionInvi);
        //查找考试时间列表
        Examdate examdate = new Examdate();
        examdate.setTid(taskPlan.getTid());
        //考试时间列表
        List<Examdate> timeList = examDateDao.selectExamTimeByCondition(examdate);
        //查询教室数量
        Room room = new Room();
        room.setTid(taskPlan.getTid());
        //所有的考试教室
        List<Room> allRoom = roomDao.selectRoomByCondition(room);
        HashMap<String, String> result = checkCondition(degrees, allTeacherList, timeList, allRoom);
        String msg = result.get("planMsg");
        boolean status = Boolean.parseBoolean(result.get("planStatus"));
        setSession(httpSession,msg,status);
        if (status){
            //检查通过
            logger.info("考生查询成功 总人数{}" , degrees.size());
            logger.info("考试教室映射表查询成功:{}",allRoom.size());
            logger.info("监考教师查询成功:{}",allTeacherList.size());
            logger.info("考试时间查询成功:{}",timeList.size());
            logger.info("准备工作完成，开始编排学位考试");
        }else {
            //检查不符合条件
            logger.info(msg);
            throw new BusinessException(msg);
        }
        //开始编排
        int flag = -1;
        if (taskPlan.getDescription().contains(ENGLISH_DEGREE)){
            flag = planEnglishStart(taskPlan,degrees,allTeacherList,timeList,allRoom);
        }else {
            flag = planComputeStart(taskPlan,degrees,allTeacherList,timeList,allRoom);
        }
        if (flag != 1){
            throw new BusinessException(" 编排过程中发生未知错误");
        }
        logger.info("学位考试编排完成...");
        taskPlan.setState("2");
        taskPlan.setEnd(taskPlan.getBegin());
        taskPlanDao.updateTaskPlanById(taskPlan);
        setSession(httpSession,"学位编排完成",true);
    }

    /**
     * 正式开始编排计算机考试
     * @param taskPlan
     * @param allDegrees
     * @param allTeacherList
     * @param timeList
     * @param allRoom
     * @return
     */
    private int planComputeStart(Taskplan taskPlan, List<Degree> allDegrees, List<Teacher> allTeacherList,
                                 List<Examdate> timeList, List<Room> allRoom) throws BusinessException {
        logger.info("准备编排计算机学位考试");
        //判断教室是否足够
        if(allDegrees.size() > countCsRoomCapacity(allRoom)){
            logger.warn("考场不足");
            throw new BusinessException("计算机学位考试考场数量不够，请继续上传");
        }
        Surface surface;
        Room chooseRoom;
        int studentNum;
        Teacher teacher1;
        Teacher teacher2;
        Details details;
        //用于存放所有的学生考试信息，最后一次存入数据库
        ArrayList<Details> detailsArrayList = new ArrayList<>();
        List<Degree> chooseDegrees ;

        while (allDegrees.size()>0){
            chooseRoom = allRoom.remove(0);
            //计算机考试中，教室的楼栋代表教室可做人数
            chooseDegrees = getRandomListByNum(allDegrees,chooseRoom.getStorid());
            surface = new Surface();
            //计算机学位考试不上传考试科目直接设置为2；
            surface.setEid(2);
            surface.setTid(taskPlan.getTid());
            surface.setNumber(chooseDegrees.size());
            surface.setBegin(timeList.get(0).getEtime());
            surface.setEnd(timeList.get(1).getEtime());
            surface.setRid(chooseRoom.getRid());
            surface.setTesttime(taskPlan.getBegin());
            teacher1 = allTeacherList.remove(0);
            teacher2 = allTeacherList.remove(0);
            surface.setTeacher(teacher1.getTname()+"-"+teacher2.getTname());
            examPlanMapper.updateTeacherOneTimes(teacher1.getNumber());
            examPlanMapper.updateTeacherOneTimes(teacher2.getNumber());
            surfaceDao.insertOneSurface(surface);
            studentNum = 1;
            for (Degree chooseDegree : chooseDegrees) {
                details = new Details();
                details.setEid(2);
                details.setSuid(surface.getSuid());
                details.setSid(chooseDegree.getSid().toString());
                details.setSname(chooseDegree.getName());
                details.setSex(chooseDegree.getSex());
                details.setTid(taskPlan.getTid());
                details.setName("学位计算机考试");
                details.setExamtime(taskPlan.getBegin());
                details.setBegin(timeList.get(0).getEtime());
                details.setEnd(timeList.get(1).getEtime());
                details.setPlace(chooseRoom.getPlace());
                details.setSeat(""+studentNum);
                details.setRid(chooseRoom.getRid());
                details.setPname(professionMapper.selectPnameByPid(chooseDegree.getPid()));
                detailsArrayList.add(details);
                studentNum++;
            }
        }
        detailsMapper.insertBatchDetails(detailsArrayList);
        return 1;
    }


    /**
     * 正式开始编排英语
     *
     * @param taskPlan
     * @param degrees 学生列表
     * @param allTeacherList
     * @param timeList
     * @param rooms
     * @return  0：编排成功
     */
    private int planEnglishStart(Taskplan taskPlan, List<Degree> degrees, List<Teacher> allTeacherList,
                           List<Examdate> timeList, List<Room> rooms) throws BusinessException {
        logger.info("准备编排英语学位考试");
        if (countRoomNum(degrees) > rooms.size()){
            logger.warn("考场数量不足");
            throw new BusinessException("考场数量不足，请继续添加考场");
        }
        Surface surface;
        Room chooseRoom;
        int studentNum;
        Teacher teacher1;
        Teacher teacher2;
        Details details;
        List<Degree> chooseDegrees;
//        ArrayList<Details> detailsArrayList = new ArrayList<>();
        //生成surface
        while (degrees.size() > 0){
            //抽取的一批学生
            chooseDegrees = getRandomListByNum(degrees, ROOM_CAPACITY);
            surface = new Surface();
            chooseRoom = rooms.remove(0);
            surface.setTid(taskPlan.getTid());
            surface.setTesttime(taskPlan.getBegin());
            surface.setBegin(timeList.get(0).getEtime());
            surface.setEnd(timeList.get(1).getEtime());
            surface.setRid(chooseRoom.getRid());
            surface.setNumber(chooseDegrees.size());
            //由于该英语学位考试不用上传考试科目所以设置为1
            surface.setEid(1);
            teacher1 = allTeacherList.remove(0);
            teacher2 = allTeacherList.remove(0);
            surface.setTeacher(teacher1.getTname()+"-"+teacher2.getTname());
            examPlanMapper.updateTeacherOneTimes(teacher1.getNumber());
            examPlanMapper.updateTeacherOneTimes(teacher2.getNumber());
            surfaceDao.insertOneSurface(surface);
            logger.info("surface的suid:{}",surface.getSuid());
            //生成detail
            studentNum = 1;
            for (Degree chooseDegree : chooseDegrees) {
                details = new Details();
                //同上
                details.setEid(1);
                details.setSuid(surface.getSuid());
                details.setSid(chooseDegree.getSid().toString());
                details.setSname(chooseDegree.getName());
                details.setSex(chooseDegree.getSex());
                details.setTid(taskPlan.getTid());
                details.setName("学位外语考试");
                details.setExamtime(taskPlan.getBegin());
                details.setBegin(timeList.get(0).getEtime());
                details.setEnd(timeList.get(1).getEtime());
                details.setPlace(chooseRoom.getPlace());
                details.setSeat(""+studentNum);
                details.setRid(chooseRoom.getRid());
                //专业名称
                details.setPname(professionMapper.selectPnameByPid(chooseDegree.getPid()));
                detailsMapper.insert(details);
                studentNum++;
            }
        }
        //纪录detail数据

        return 0;
    }

    /**
     * 排考前的检查
     * @param degrees 进行学位考试的学生列表
     * @param allTeacherList 监考教师列表
     * @param examdate 考试时间
     * @param room 考试教室
     * @return 检查后的信息
     */
    public HashMap<String,String> checkCondition(List<Degree> degrees, List<Teacher> allTeacherList, List<Examdate> examdate,
                                                 List<Room> room){
        HashMap<String,String> info = new HashMap<>(16);
        if (degrees.size() == 0){
            info.put("planMsg","学生信息未上传");
            info.put("planStatus","false");
            return info;
        }
        if (allTeacherList.size() == 0 ){
            info.put("planMsg","监考教师信息未上传");
            info.put("planStatus","false");
            return info;
        }
        if (room.size() * 2 > allTeacherList.size()){
            info.put("planMsg","监考教师数量不足");
            info.put("planStatus","false");
            return info;
        }
        if (examdate.size() < 2 ){
            info.put("planMsg","考试时间上传错误");
            info.put("planStatus","false");
            return info;
        }
        if (room.size() == 0 ){
            info.put("planMsg","考场未上传");
            info.put("planStatus","false");
            return info;
        }

        info.put("planMsg","检查工作完成，开始编排学位考试");
        info.put("planStatus","true");
        return info;
    }

    private void setSession(HttpSession httpSession ,String msg, boolean flag){
        httpSession.setAttribute("planMsg",msg);
        httpSession.setAttribute("planStatus",flag);
    }

    /**
     * 计算英语考试所需教室数量
     * @param degrees 参加考试的人数
     * @return 所需要的教室数量
     */
    private int countRoomNum(List<Degree> degrees){

        int num = degrees.size() / 30;
        if (degrees.size() % ROOM_CAPACITY != 0 ){
            num++;
        }
        return num;
    }

    /**
     * 计算计算机学位考试所给的教室可坐多少人
     * @param roomList
     * @return
     */
    private int countCsRoomCapacity(List<Room> roomList){
        int sum = 0;
        for (Room room : roomList) {
            //原表中的楼栋 代表教室容量
            sum += room.getStorid();
        }
        return sum;
    }


    /**
     * 从给定的数组中，随机抽取num个数,并从原数组中删除这些元素
     * 如果数组大小 < 所需要的长度 直接返回该数组
     * @param degrees 目标数组
     * @param num 数量
     * @return
     */
    private List<Degree> getRandomListByNum(List<Degree> degrees, int num){
        ArrayList<Degree> targetList = new ArrayList<>(num);
        if (num > degrees.size()){
            targetList.addAll(degrees);
            degrees.clear();
            return targetList;
        }
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            int index = random.nextInt(degrees.size());
            targetList.add(degrees.get(index));
            //将获取到的值，从原数组中删除
            degrees.remove(index);
        }
        return targetList;
    }

    /**
     * 上传学位考试学生信息
     * @param tid
     * @param file
     * @param session
     */
    @Transactional(rollbackFor = Exception.class)
    public void uploadDegreeInfo(Integer tid, MultipartFile file, HttpSession session) throws Exception {
        List<String> propertys= Arrays.asList("sid","name","sex","level","clazz","pid","cid","grade","phone");
        List<Degree> degreeList = FileUtil.readCommonalityExcel(file,0,propertys,new Teacher());
        if (degreeList.size() == 0){
            logger.warn("上传文件信息异常");
            throw new BusinessException("上传文件信息异常");
        }
        for (Degree degree : degreeList) {
            degree.setTid(tid);
        }
        int row = degreeMapper.insertBatchDegreeInfo(degreeList);
        if (row != degreeList.size()){
            logger.warn("插入信息时出错");
            throw new BusinessException("插入出现异常，请重新上传");
        }
    }

    /**
     * 根据指定的名称范围获取教室，并从原数组中移除选中的教室
     * @param roomList
     * @param place 给定的范围
     * @return 符合条件的教室
     */
    private List<Room> getRoomByPlace(List<Room> roomList , String[] place){
         ArrayList<Room> arrayList = new ArrayList<>();
         Room room ;
        for (int i = 0; i < roomList.size(); i++) {
            room = roomList.get(i);
            for (String s : place) {
                if (room.getPlace().equals(s)){
                    arrayList.add(room);
                    roomList.remove(i);
                    i--;
                }
            }
        }
        return arrayList;
    }


    /**
     * 生成学位考试的准考证号
     * 生成后的压缩包放在 服务器路径/zip.zip
     * @param session
     * @param response 输出流
     * @param tid 一次学位考试下的第二场学位考试的tid
     * @return 压缩好的压缩包
     */
    public void getDegreeAdmissionCards(HttpSession session, HttpServletResponse response , int tid){
        List<AdmissionCard > admissionCardList = degreeMapper.getDegreeAdmissionCards(tid-1,tid);
        logger.info("数据量{},tid{}",admissionCardList.size(),tid);

        List<Details> details = detailsMapper.selectByTid(tid);
        details.addAll(detailsMapper.selectByTid(tid-1));
        logger.info("details的大小{}",details.size());
        detailsMatchName(admissionCardList,details);
        setAdmissionCardTime(admissionCardList);
        logger.info("准备生成准考证。。。。。。");
        String location = session.getServletContext().getRealPath(File.separator+"admissionCard");
        cn.hutool.core.io.FileUtil.mkdir(location);
        //用来纪录生成的pdf路径
        List<String> paths = new ArrayList<>();
        //20**-20**学年度下学期
        String title = taskplanMapper.getTitle(tid).substring(0,15);
        String path;
        for (AdmissionCard admissionCard : admissionCardList) {
            //存放路径为 /admissionCards/姓名.pdf;
            path = location+File.separator+admissionCard.getName()+".pdf";
            ReportUtil.admissionTicket(admissionCard,path,title+"学位考试",session);
            paths.add(path);
        }
        //将location目录下的所有文件打包成一个压缩包
        logger.info("location is {}",location);
        File file = ZipUtil.zip(location, session.getServletContext().getRealPath(File.separator + "zip.zip"));

        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            outputStream = response.getOutputStream();
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //删除所有的pdf
        cn.hutool.core.io.FileUtil.del(location);
        cn.hutool.core.io.FileUtil.del(file);

    }

    /**
     * 将考试安排以姓名匹配的方式匹配
     * @param admissionCards
     * @param details
     */
    private void detailsMatchName(List<AdmissionCard> admissionCards , List<Details> details){
        AdmissionCard admissionCard = null;
        List<Details> list ;
        for (int i = 0 ; i < admissionCards.size(); i++){
            admissionCard = admissionCards.get(i);
            list = new ArrayList<>();
            admissionCard.setDetails(list);
            for (Details detail : details) {
                if (admissionCard.getName().equals(detail.getSname())){
                    logger.info("学生姓名：{},detail姓名：{}",admissionCard.getName(),detail.getSname());
                    admissionCard.getDetails().add(detail);
                }
            }
            logger.info("{}有{}场考试",admissionCard.getName(),admissionCard.getDetails().size());
        }
    }

    private void setAdmissionCardTime(List<AdmissionCard> admissionCardList){
        for (int i = 0; i < admissionCardList.size(); i++) {
            List<Details> details = admissionCardList.get(i).getDetails();
            for (Details detail : details) {
                detail.setTime(DateTimeUtil.showDate(detail.getExamtime())+" "+ DateTimeUtil.showTime(detail.getBegin())+"-"+DateTimeUtil.showTime(detail.getEnd()));
            }
        }
    }

}
