package cn.edu.hbuas.examsys.service.examplan;

import cn.edu.hbuas.examsys.dao.ArrangeDao;
import cn.edu.hbuas.examsys.dao.TaskPlanDao;
import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author SFX
 */
@Service
public class ExamUtilService {

    @Autowired
    private TaskPlanDao taskPlanDao;
    @Autowired
    private ArrangeDao arrangeDao;

    private static Logger logger = LoggerFactory.getLogger(ExamUtilService.class);

    /**
     * 从总的考试科目选取指定年级的考试科目
     * @param source 总的考试科目列表
     * @param grade 选择的考试科目列表
     * @return 考试科目列表
     */
    public List<Examcourse> chooseGradeCourse(List<Examcourse> source,HashMap<String,Integer> batchNameMap,String grade,HashMap<String,Integer> marks){
        List<Examcourse> examcourseList = new ArrayList<>();
        //首先选取优先级高的科目
        for (Examcourse choose:source
             ) {
            if(choose.getGrade().equals(grade)){
                if((!marks.containsKey(choose.getClazz()))&&(!batchNameMap.containsKey(choose.getName()))){
                    if((choose.getPriority() != null)&&(choose.getPriority() == 1)){
                        examcourseList.add(choose);
                        marks.put(choose.getClazz(),1);
                    }
                }
            }
        }
        //选取普通科目
        for (Examcourse choose:source
        ) {
            if(choose.getGrade().equals(grade)){
                if((!marks.containsKey(choose.getClazz()))&&(!batchNameMap.containsKey(choose.getName())))
                if(!marks.containsKey(choose.getClazz())){
                    examcourseList.add(choose);
                    marks.put(choose.getClazz(),1);
                }
            }
        }
        return examcourseList;
    }

    /**
     * 判断考试科目是否是同批考试
     * @param batches 同批考试列表
     * @param examcourse 考试科目
     * @return 在返回 true ，不在返回 false
     */
    private boolean isInBatch(List<Batch> batches,Examcourse examcourse){
        for (Batch batch:batches){
            if(examcourse.getName().equals(batch.getBname()))
                return true;
        }
        return false;
    }


    /**
     * 获取所有的考试科目名称
     * @param allBatchs
     * @return
     */
    public HashMap<String,Integer> getAllBatchNameMap(List<Batch> allBatchs){
        HashMap<String,Integer> batchName = new HashMap<>();
        for (Batch batch:allBatchs){
            String[] examStr = batch.getBname().split("-");
            for (String str:examStr){
                batchName.put(str,1);
            }
        }
        return batchName;
    }

    /**
     * 根据年级选取校级同批考试
     * @param source 所有同批考试数据
     * @param grade 年级
     * @return 选取的一个同批考试
     */
    public Batch getSchoolBatch(List<Batch> source,String grade){
        for (Batch choose:source){
            if(choose.getType().equals("0")&&grade.equals(choose.getGrade())){
                return choose;
            }
        }
        return null;
    }


    /**
     * 选取校级同批考试列表
     * @param source 所有同批考试数据
     * @return 选取所有校级同批考试
     */
    public List<Batch> getSchoolBatchList(List<Batch> source){
        List<Batch> batchList = new ArrayList<>();
        for (Batch choose:source){
            if(choose.getType().equals("0")){
                batchList.add(choose);
            }
        }
        return batchList;
    }

    /**
     * 根据年级选取院级同批考试列表
     * @param source 所有同批考试
     * @param grade 选择年级
     * @return 选择的结果列表
     */
    public List<Batch> getCollegeBatchList(List<Batch> source,String grade){
        List<Batch> result = new ArrayList<>();
        for (Batch batch:source){
            if( (!batch.getType().equals("0")) && batch.getGrade().equals(grade)){
                result.add(batch);
            }
        }
        return result;
    }


    /**
     * 计算列表中的考试科目一共需要多少教室
     * @param examcourses
     * @return
     */
    public int countNeedRoom(List<Examcourse> examcourses){
        int total = 0;
        int num = 0;
        for (Examcourse examcourse:examcourses){
            num = examcourse.getTotal()%30;
            total = total + examcourse.getTotal()/30;
            if(num > 0){
                total++;
            }
        }
        logger.info("考试科目名称：{} 考试科目数量:{}  需要教室：{}",examcourses.get(0).getName(),examcourses.size(),total);
        return total;
    }

    public int countNeedRoom(Examcourse examcourse){
        int total;
        int num;
        num = examcourse.getTotal()%30;
        total = examcourse.getTotal()/30;
        if(num > 0){
            total++;
        }
        return total;
    }

    /**
     * 获取分教室之后的考生与考场情况
     * @param examineeList
     * @return
     */
    public List<List<Examinee>> getStudentRoomList(List<Examinee> examineeList){
        List<List<Examinee>> examRoomList = new ArrayList<>();
        List<Examinee> newExamList = new ArrayList<>();
        int flag = 0;
        for (Examinee examinee:examineeList){
            if(flag >= 30){
                flag = 0;
                examRoomList.add(newExamList);
                newExamList = new ArrayList<>();
            }
            newExamList.add(examinee);
            flag++;
        }
        examRoomList.add(newExamList);
        return examRoomList;
    }

    /**
     * 编排院级同批
     * 按照同批科目名称选取同批考试数据,可以选择多个科目，中间以 “ - ”分割,并且检测同批考试出现重复班级
     * @param source 所有科目列表
     * @param name 同批科目名称
     * @return 选取的科目列表, Null表示当前班级已经参加过考试，list.size() = 0表示找不到这个科目名称的班级
     */
    public List<Examcourse> chooseBatchExam(List<Examcourse> source,String name,HashMap<String,Integer> clazzNameMap,StringBuffer stringBuffer){
        List<Examcourse> examcourseList = new ArrayList<>();
        String[] examStr = name.split("-");
        for (Examcourse choose:source) {
            for (int i = 0; i < examStr.length;i++){
                if(examStr[i].equals(choose.getName())){
                    examcourseList.add(choose);
                }
            }
        }
        HashMap<String,String> hashMap = new HashMap<>();
        for (Examcourse examcourse:examcourseList){
            if(hashMap.containsKey(examcourse.getClazz())){
                String info = "同批考试出现冲突,但是算法忽略改异常继续编排，需要管理员判断是否需要重新编排："+examcourse.getClazz()+":"+examcourse.getName()+"-"+hashMap.get(examcourse.getClazz())+" . ";
                logger.error(info);
                stringBuffer.append(info);
            }else {
                hashMap.put(examcourse.getClazz(),examcourse.getName());
                if(clazzNameMap.containsKey(examcourse.getClazz()))
                    return null;
            }
        }
        return examcourseList;
    }

    /**
     * 对原始的监考老师进行升序排列，然后返回一个新的数组
     * @param source
     * @return
     */
    public List<Teacher> sortAndCopyTeactherList(List<Teacher> source){
        List<Teacher> to = new ArrayList<>();
        Collections.sort(source, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return o1.getTimes() - o2.getTimes();
            }
        });
        for (Teacher teacher:source){
            to.add(teacher);
        }
        return to;
    }


    public List<Teacher> copyTeactherList(List<Teacher> source){
        List<Teacher> to = new ArrayList<>();
        for (Teacher teacher:source){
            to.add(teacher);
        }
        return to;
    }


    /**
     * 按照考试科目名称选取同批考试数据
     * 多个同批考试
     * @param source 所有科目列表
     * @param name 科目名称
     * @return 选取的科目列表
     */
    public HashMap<String,List<Examcourse>> chooseMultiBatchExam(List<Examcourse> source, String name, StringBuffer stringBuffer){
        HashMap<String,List<Examcourse>> stringListHashMap = new HashMap<>();
        String[] examStr = name.split("-");
        List<Examcourse> examcourseList;
        List<Examcourse> batchCourseList = new ArrayList<>();
        for (int i = 0;i < examStr.length;i++){
            examcourseList = new ArrayList<>();
            for (Examcourse choose:source) {
                if(examStr[i].equals(choose.getName())){
                    examcourseList.add(choose);
                }
            }
            batchCourseList.addAll(examcourseList);
            stringListHashMap.put(examStr[i],examcourseList);
        }
        HashMap<String,String> integerHashMap = new HashMap<>();
        for (Examcourse examcourse:batchCourseList){
            if(integerHashMap.containsKey(examcourse.getClazz())){
                String info = "检测到冲突课程，但是忽略错误继续编排，请管理员判断是否重新编排："+examcourse.getClazz()+":"+examcourse.getName()+"-"+integerHashMap.get(examcourse.getClazz())+" . ";
                logger.error(info);
                stringBuffer.append(info);
            }
            else
                integerHashMap.put(examcourse.getClazz(),examcourse.getName());
        }
        return stringListHashMap;
    }


    /**
     * 从所有的监考老师列表中选取指定院系的指定数量的老师
     * @param source 全部监考老师列表
     * @param cid 院系编号
     * @param num 选取的数量
     * @return 选取成功则返回指定数量的老师列表，选取失败则返回NUll
     */
    public List<Invigilation> chooseNumInvi(List<Invigilation> source,int cid,int num){
        List<Invigilation> resultList = new ArrayList<>();
        for (Invigilation invi:source
             ) {
            if(invi.getCid() == cid){
                resultList.add(invi);
                if(resultList.size() == num)
                    return resultList;
            }
        }
        return null;
    }

    /**
     * 从所有的监考老师列表中选取指定院系的指定数量的老师
     * @param source 全部监考老师列表
     * @param cid 院系编号
     * @param num 选取的数量
     * @return 选取成功则返回指定数量的老师列表，选取失败则返回NUll
     */
    public List<Teacher> chooseNumInviTeacher(List<Teacher> source, int cid, int num){
        List<Teacher> resultList = new ArrayList<>();
        for (Teacher invi:source
        ) {
            if(invi.getCid() == cid){
                resultList.add(invi);
                if(resultList.size() == num)
                    return resultList;
            }
        }
        return resultList;
    }

    //按照院别聚集校级同批考试考生
    public HashMap<Integer,List<Examinee>> getCollegeStudent(Integer tid,List<Examcourse> examcourseList) throws Exception{
        HashMap<Integer,List<Examinee>> integerListHashMap = new HashMap<>();
        List<Examinee> examineeList;
        for (Examcourse examcourse:examcourseList){
            if(!integerListHashMap.containsKey(examcourse.getCid()))
                integerListHashMap.put(examcourse.getCid(),new ArrayList<>());
            examineeList = arrangeDao.selectExamineeListByTidEidByRand(tid,examcourse.getEid());
            copyExaminee(integerListHashMap.get(examcourse.getCid()),examineeList,examcourse);
        }
        return integerListHashMap;
    }

    /**
     * 把
     * @param orign
     * @param targat
     */
    private void copyExaminee(List<Examinee> orign,List<Examinee> targat,Examcourse examcourse){
        for (Examinee examinee:targat){
            examinee.setEid(examcourse.getEid());
            orign.add(examinee);
        }
    }

    /**
     * 定时任务，更新考试任务状态
     */
    public void updateExamPlanState(){
        Date now = new Date();
        List<Taskplan> taskplanList = taskPlanDao.selectTaskByState("2","3");
        for (Taskplan taskplan:taskplanList) {
            if(now.getTime() > taskplan.getEnd().getTime()){
                taskplan.setState("4");
                taskPlanDao.updateTaskPlanById(taskplan);
            }else if ((now.getTime() >= taskplan.getBegin().getTime())&&(now.getTime() <= taskplan.getEnd().getTime())){
                taskplan.setState("3");
                taskPlanDao.updateTaskPlanById(taskplan);
            }
        }
    }

}
