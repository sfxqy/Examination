package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.Date;


public class SurfaceDTO {
    private Integer suid;

    private String teacherOne;

    private String teacherTwo;

    /**
     * 考试科目名称
     */
    private String examName;

    private String roomPlace;

    private String testtime;

    private String begin;

    private String end;

    private String workNumberOne;

    private String workNumberTwo;


    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public String getTeacherOne() {
        return teacherOne;
    }

    public void setTeacherOne(String teacherOne) {
        this.teacherOne = teacherOne;
    }

    public String getTeacherTwo() {
        return teacherTwo;
    }

    public void setTeacherTwo(String teacherTwo) {
        this.teacherTwo = teacherTwo;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getRoomPlace() {
        return roomPlace;
    }

    public void setRoomPlace(String roomPlace) {
        this.roomPlace = roomPlace;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getWorkNumberOne() {
        return workNumberOne;
    }

    public void setWorkNumberOne(String workNumberOne) {
        this.workNumberOne = workNumberOne;
    }

    public String getWorkNumberTwo() {
        return workNumberTwo;
    }

    public void setWorkNumberTwo(String workNumberTwo) {
        this.workNumberTwo = workNumberTwo;
    }

    @Override
    public String toString() {
        return "SurfaceDTO{" +
                "suid=" + suid +
                ", teacherOne='" + teacherOne + '\'' +
                ", teacherTwo='" + teacherTwo + '\'' +
                ", examName='" + examName + '\'' +
                ", roomPlace='" + roomPlace + '\'' +
                ", testtime=" + testtime +
                ", begin=" + begin +
                ", end=" + end +
                ", workNumberOne='" + workNumberOne + '\'' +
                ", workNumberTwo='" + workNumberTwo + '\'' +
                '}';
    }
}
