package cn.edu.hbuas.examsys.service;

import cn.edu.hbuas.examsys.model.Constants;
import cn.edu.hbuas.examsys.mybatis.mapper.DetailsMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.NoteMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Note;
import cn.edu.hbuas.examsys.mybatis.pojo.NoteDTO;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherArrange;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.DateTimeUtil;
import cn.edu.hbuas.examsys.utils.NoteUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class NoteService {

    private static Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    DetailsMapper detailsMapper;
    @Autowired
    NoteMapper noteMapper;

    /**
     * 根据给定的map集合发送短信
     *
     * @param hashMap
     */
    private void sendNote(HashMap<String, Note> hashMap) {
        Note note;
        int size;
        Set<Map.Entry<String, Note>> entrySet = hashMap.entrySet();
        for (Map.Entry<String, Note> entry : entrySet) {
            note = entry.getValue();
            size = note.getTeacherArrangeList().size();
            NoteUtil.generallySendNote(note, size);
        }
    }

    /**
     * 根据工号返回对应的电话号码
     * @param workNumber 要查询人的工号
     * @return 此人的电话号码
     */
    private String getPhoneByWorkNumber(String workNumber, List<Teacher> teachers) throws BusinessException {

        for (Teacher teacher : teachers) {
            if (teacher.getNumber().equals(workNumber)) {
                return teacher.getPhone();
            }
        }
        throw new BusinessException("查询不到对应老师的电话号码" + "...教师工号：" + workNumber);
    }

    /**
     * 发送学位考试的短信通知
     *
     * @param tid 第二场学位考试的tid
     */
    public void sendDegreeNote(Integer tid) {
        noteMapper.selectDegreeArrangeByTid(tid - 1, tid);
    }


    /**
     * 于前一天的下午5点自动发送短信
     */
    @Scheduled(cron = "0 0 17 * * ?")
    public void autoSendNote() throws BusinessException {
        logger.info("自动发送短信。。。。。");
        Date date = new Date();
        Date date1 = DateTimeUtil.dateAddDay(date, 1);
        String time = DateTimeUtil.showDate(date1);
        //先判断今天是否有考试安排
        logger.info(time);
        Integer exams = noteMapper.haveExam(time);
        if (exams == 0){
            return;
        }
        Integer tid = noteMapper.getTidByTime(time);
        logger.info(tid.toString());
        //现获取该日的全部监考信息
        List<NoteDTO> noteDTOS = noteMapper.selectTeacherArrangeByTime(time, tid);
        logger.info("今天共有：{}场考试安排", noteDTOS.size());
        //存放最终的发送集合
        HashMap<String, Note> hashMap = new HashMap<>(16);
        //查询出所有的教师工号和对应号码
        List<Teacher> teacherList = noteMapper.selectInvigilationInfoByTid(tid);
        Note note;
        TeacherArrange teacherArrange;
        String[] workNumbers = null;
        String[] names = null;
        ArrayList<TeacherArrange> list;
        for (NoteDTO noteDTO : noteDTOS) {
            teacherArrange = new TeacherArrange();
            teacherArrange.setRoomPlace(noteDTO.getPlace());
            teacherArrange.setExamTime(DateTimeUtil.showDate(noteDTO.getTesttime()));
            teacherArrange.setBegin(DateTimeUtil.showTime(noteDTO.getBegin()));
            teacherArrange.setEnd(DateTimeUtil.showTime(noteDTO.getEnd()));
            teacherArrange.setExamName(noteDTO.getName());

            if (noteDTO.getWorknumber() != null){
                workNumbers = noteDTO.getWorknumber().split("-");
                names = noteDTO.getTeacher().split("-");
                for (int i = 0 ; i < workNumbers.length ; i ++) {
                    if (!hashMap.containsKey(workNumbers[i])){
                        //将该工号对应的老师放入map
                        note = new Note();
                        note.setWorkNumber(workNumbers[i]);
                        note.setName(names[i]);
                        note.setPhone(getPhoneByWorkNumber(workNumbers[i],teacherList));
                        list = new ArrayList<>();
                        list.add(teacherArrange);
                        note.setTeacherArrangeList(list);
                        hashMap.put(workNumbers[i],note);
                    }else {
                        hashMap.get(workNumbers[i]).getTeacherArrangeList().add(teacherArrange);
                    }
                }
            }
        }
        logger.info("信息准备发送...共{}位教师",hashMap.size());
        sendNote(hashMap);
        logger.info("发送成功");
    }
}
