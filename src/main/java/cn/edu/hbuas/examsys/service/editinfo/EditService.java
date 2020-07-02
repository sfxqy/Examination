package cn.edu.hbuas.examsys.service.editinfo;

import cn.edu.hbuas.examsys.dao.EditInfoDao;
import cn.edu.hbuas.examsys.mybatis.mapper.EditInfoMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.RoomMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.SurfaceMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.TeacherMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.mybatis.pojo.Room;
import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import cn.edu.hbuas.examsys.mybatis.pojo.SurfaceDTO;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.DateTimeUtil;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 牛传喜
 */
@Service
public class EditService {
    @Autowired
    private EditInfoDao editInfoDao;
    @Autowired
    private SurfaceMapper surfaceMapper;
    @Autowired
    private EditInfoMapper editInfoMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private TeacherMapper teacherMapper;


    /**
     * 先根据考试任务的id和本院id查询本次所有的监考教师情况
     * 然后封装为dto对象
     *
     * @param tid
     * @param cid
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<SurfaceDTO> getSurfaceInfoByCid(int tid, int cid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Surface> surfaceList = editInfoMapper.getSurfaceInfoByCid(tid, cid);
        //查询所有的教室地点
        List<Room> roomList = editInfoDao.selectRoomByTid(tid);
        //查询本院考试科目
        List<Examcourse> examcourseList = editInfoDao.selectExamcourseByCid(cid, tid);
        //开始生成dto对象
        List<SurfaceDTO> dtoList = new ArrayList<>();
        SurfaceDTO surfaceDTO;
        for (Surface surface : surfaceList) {
            surfaceDTO = new SurfaceDTO();
            //装填教师工号
            if (surface.getWorknumber() != null) {
                String[] split = surface.getWorknumber().split("-");
                surfaceDTO.setWorkNumberOne(split[0]);
                if (split.length == 2) {
                    surfaceDTO.setWorkNumberTwo(split[1]);
                }
            }
            //装填教师姓名
            if (surface.getTeacher() != null) {
                String[] teachers = surface.getTeacher().split("-");
                surfaceDTO.setTeacherOne(teachers[0]);
                if (teachers.length == 2) {
                    surfaceDTO.setTeacherTwo(teachers[1]);
                }
            }
            //装填考试时间
            surfaceDTO.setBegin(DateTimeUtil.showTime(surface.getBegin()));
            surfaceDTO.setEnd(DateTimeUtil.showTime(surface.getEnd()));
            surfaceDTO.setTesttime(DateTimeUtil.showDate(surface.getTesttime()));
            //装填教室地址
            surfaceDTO.setRoomPlace(getRoomPlaceByRid(surface.getRid(), roomList));
            //装填考试科目名称
            surfaceDTO.setExamName(getExamNameByEid(surface.getEid(), examcourseList));
            //装填主键
            surfaceDTO.setSuid(surface.getSuid());
            dtoList.add(surfaceDTO);
        }
        return dtoList;

    }

    /**
     * 根据eid，在指定的list中查询对应考试名称
     *
     * @param eid
     * @param examcourseList
     * @return
     */
    private String getExamNameByEid(int eid, List<Examcourse> examcourseList) {
        String examName;
        for (Examcourse examcourse : examcourseList) {
            if (examcourse.getEid().equals(eid)) {
                examName = examcourse.getName();
                return examName;
            }
        }
        return null;
    }

    /**
     * 根据rid获取考试地点
     *
     * @param rid
     * @param roomList
     * @return
     */
    private String getRoomPlaceByRid(int rid, List<Room> roomList) {
        String name;
        for (Room room : roomList) {
            if (room.getRid().equals(rid)) {
                name = room.getPlace();
                return name;
            }
        }
        return null;
    }

    /**
     * 修改监考教师安排
     * 存在无监考监考教师的情况
     *
     * @param surfaceDTO
     * @return 1： 修改成功  0 ：监考教师在该时间段有安排
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateSurfaceInfo(SurfaceDTO surfaceDTO) throws BusinessException {
        if (surfaceDTO.getWorkNumberOne() == null && surfaceDTO.getWorkNumberTwo() == null) {
            throw new BusinessException("传入的监考教师信息不全");
        }
        //查询传入的监考教师在本考试时间段是否还有其他考试

        //先根据本次要修改的安排主键查询出原安排
        Surface beforeSurface = surfaceMapper.selectByPrimaryKey(surfaceDTO.getSuid());
        //先查询出本场考试的时间段下所有的老师工号
        List<String> allWorkNumberByTime = editInfoMapper.getAllWorkNumberByTime(beforeSurface.getTesttime(), beforeSurface.getBegin());


        //之前的监考教师存在
        if (beforeSurface.getWorknumber() != null) {
            //得到之前的工号
            String worknumber = beforeSurface.getWorknumber();
            String[] beforeTeachers = worknumber.split("-");
            //得到更改后的两位教师工号
//            String[] editTeachers = new String[2];
            ArrayList<String> editTeachers = new ArrayList<>();
            if (surfaceDTO.getWorkNumberOne() != null){
                editTeachers.add(surfaceDTO.getWorkNumberOne());
            }
            if (surfaceDTO.getWorkNumberTwo() != null){
                editTeachers.add(surfaceDTO.getWorkNumberTwo());
            }
            //比较两次工号的不同
            HashMap<String, ArrayList<String>> map = diffTeacher(beforeTeachers, editTeachers);
            ArrayList<String> deleteTeacher = map.get("deleteTeacher");
            ArrayList<String> addTeacher = map.get("addTeacher");
            //先查询所有的工号
            List<String> allWorkNumber = editInfoMapper.getAllWorkNumber();
            //检查添加的工号是否正确
            for (String s : addTeacher) {
                boolean flag = workNumberIsRight(s,allWorkNumber);
                if (!flag){
                    throw new BusinessException("添加的工号不正确");
                }
            }
            //查看增加的教师是否有其他监考
            for (String s : addTeacher) {
                boolean flag = workNumberInList(s,allWorkNumberByTime);
                if (flag){
                    return 0;
                }
            }
            //将被删除的教师的监考次数-1
            if (deleteTeacher.size() != 0) {
                for (String s : deleteTeacher) {
                    editInfoMapper.subTeacherTimeByNumber(s);
                }
            }
            //新增加的教师的监考次数+1
            if (addTeacher.size() != 0) {
                for (String s : addTeacher) {
                    int row = editInfoMapper.addTeacherTimeByNumber(s);
                }
            }
        } else {
//            之前的监考教师为空，传入的都为新增教师
            editInfoMapper.addTeacherTimeByNumber(surfaceDTO.getWorkNumberOne());
            editInfoMapper.addTeacherTimeByNumber(surfaceDTO.getWorkNumberTwo());

        }
        //更新修改后的监考信息
        Surface surface = new Surface();
        surface.setSuid(surfaceDTO.getSuid());
        String workNumberOne = "";
        String workNumberTwo = "";
        String teacherNameOne = "";
        String teacherNameTwo = "";
        if (beforeSurface.getWorknumber() != null && !beforeSurface.getWorknumber().equals("")){
            String[] numbers = beforeSurface.getWorknumber().split("-");
            if (numbers.length == 2) {
                workNumberOne = beforeSurface.getWorknumber().split("-")[0];
                workNumberTwo = beforeSurface.getWorknumber().split("-")[1];
                teacherNameOne = beforeSurface.getTeacher().split("-")[0];
                teacherNameTwo = beforeSurface.getTeacher().split("-")[1];
            }
            if (numbers.length == 1){
                workNumberOne = beforeSurface.getWorknumber().split("-")[0];
                teacherNameOne = beforeSurface.getTeacher().split("-")[0];
            }
        }

        if(surfaceDTO.getWorkNumberOne() != null){
            workNumberOne = surfaceDTO.getWorkNumberOne();
            teacherNameOne = editInfoMapper.getTeacherNameByWorkNumber(workNumberOne);
        }
        if (surfaceDTO.getWorkNumberTwo() != null){
            workNumberTwo = surfaceDTO.getWorkNumberTwo();
            teacherNameTwo = editInfoMapper.getTeacherNameByWorkNumber(workNumberTwo);
        }

        surface.setWorknumber(workNumberOne+"-"+workNumberTwo);
        surface.setTeacher(teacherNameOne+"-"+teacherNameTwo);
        editInfoDao.updateSurface(surface);
        return 1;
    }

    /**
     * 检查工号的正确性
     * @param s 要检查的教师工号
     * @param allWorkNumber 所有的教师工号
     * @return true 工号正确
     */
    private boolean workNumberIsRight(String s, List<String> allWorkNumber) {
        for (String s1 : allWorkNumber) {
            if (s1.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param workNumber
     * @param workNumberList
     * @return true: 存在该老师
     */
    private boolean workNumberInList(String workNumber, List<String> workNumberList){
        for (String s : workNumberList) {
            if (s == null) {
                continue;
            }
            if (s.contains(workNumber)){
                return true;
            }
        }
        return false;
    }




    /**
     * 比较两次的监考教师名单，得到新添加的教师姓名，以及被删除的教师姓名
     * deleteTeacher 放的是被删除的教师姓名
     * addTeacher 放的是新增加的教师姓名
     *
     * @param before 该场考试未修改前的监考教师姓名
     * @param edit   修改后的教师姓名
     * @return
     */
    private HashMap<String, ArrayList<String>> diffTeacher(String[] before, ArrayList<String> edit) {
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>(16);
        hashMap.put("deleteTeacher", new ArrayList<String>());
        hashMap.put("addTeacher", new ArrayList<String>());
        String[] edits = (String[]) edit.toArray(new String[edit.size()]);
        //求被删除的教师姓名
        for (String s : before) {
            boolean flag = false;
            for (String s1 : edits) {
                if (s.equals(s1)) {
                    flag = true;
                }
            }

            if (!flag) {
                hashMap.get("deleteTeacher").add(s);
            }
        }
        //求新增加的教师姓名
        for (String s : edits) {
            boolean flag = true;
            for (String s1 : before) {
                if (s.equals(s1)) {
                    flag = false;
                }
            }
            if (flag) {
                hashMap.get("addTeacher").add(s);
            }
        }
        return hashMap;
    }


    /**
     * 根据传入教师姓名和科目查询对应的监考信息
     * @param cid
     * @param tid
     * @param teacherName
     * @param subjectName
     * @return
     */
    public List<SurfaceDTO> getSurfaceByTeacherNameAndSubject(Integer cid, Integer tid, String teacherName, String subjectName) {
        //先把本院的所有监考情况查询出来
        List<SurfaceDTO> surfaceInfo = getSurfaceInfoByCid(tid, cid, 1, 500);
        //再查询对应的信息
        List<SurfaceDTO> surfaceDTOList;
        if (StringUtils.isEmpty(teacherName)){
            //根据科目名称查询
            surfaceDTOList  = getSurfaceBySubjectName(subjectName,surfaceInfo);
        }else if (StringUtils.isEmpty(subjectName)){
            //根据教师名称查询科目
            surfaceDTOList = getSurfaceByTeacherName(teacherName,surfaceInfo);
        }else {
            //双条件查询
            surfaceDTOList = getSurfaceBySubjectNameAndSubject(teacherName,subjectName,surfaceInfo);
        }
        return surfaceDTOList;
    }

    /**
     * 根据传入的教师姓名和科目名称查询对应的监考信息
     * @param teacherName
     * @param subjectName
     * @param surfaceInfo
     * @return
     */
    private List<SurfaceDTO> getSurfaceBySubjectNameAndSubject(String teacherName, String subjectName, List<SurfaceDTO> surfaceInfo) {
        List<SurfaceDTO> result = new ArrayList<>(); ;

        for (SurfaceDTO surfaceDTO : surfaceInfo) {
            if (surfaceDTO.getExamName().contains(subjectName)){
                if(surfaceDTO.getTeacherOne().equals(teacherName) || surfaceDTO.getTeacherTwo().equals(teacherName)){
                    result.add(surfaceDTO);
                }
            }
        }
        return result;
    }

    /**
     * 根据教师姓名查询对应的监考信息
     * @param teacherName
     * @param surfaceInfo
     * @return
     */
    private List<SurfaceDTO> getSurfaceByTeacherName(String teacherName, List<SurfaceDTO> surfaceInfo) {
        List<SurfaceDTO> result = new ArrayList<>();
        for (SurfaceDTO surfaceDTO : surfaceInfo) {
            if (surfaceDTO.getTeacherOne().equals(teacherName) || surfaceDTO.getTeacherTwo().equals(teacherName)){
                result.add(surfaceDTO);
            }
        }
        return result;
    }

    /**
     * 在已有的监考信息中 根据科目名称查询特定的 监考信息
     * @param subjectName
     * @param surfaceInfo
     * @return
     */
    private List<SurfaceDTO> getSurfaceBySubjectName(String subjectName, List<SurfaceDTO> surfaceInfo) {
        List<SurfaceDTO> result = new ArrayList<>();
        for (SurfaceDTO surfaceDTO : surfaceInfo) {
            if (surfaceDTO.getExamName().contains(subjectName)){
                result.add(surfaceDTO);
            }
        }
        return result;
    }
}
