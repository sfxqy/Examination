package cn.edu.hbuas.examsys.mybatis.pojo;


import java.util.List;

/**
 * @author SFX
 * 考生信息册
 */
public class StudentStatement {

    //
    private Integer suid;

    //考场编号
    private Integer rid;

    //具体教室
    private String place;

    //考试科目
    private String name;

    //考试时间
    private String time;

    //监考教师
    private String teacher;

    //具体学生安排
    private List<AdmissionCard> admissionCardList;


    public StudentStatement() {
    }

    public StudentStatement(Integer suid, Integer rid, String place, String name, String time, String teacher, List<AdmissionCard> admissionCardList) {
        this.suid = suid;
        this.rid = rid;
        this.place = place;
        this.name = name;
        this.time = time;
        this.teacher = teacher;
        this.admissionCardList = admissionCardList;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<AdmissionCard> getAdmissionCardList() {
        return admissionCardList;
    }

    public void setAdmissionCardList(List<AdmissionCard> admissionCardList) {
        this.admissionCardList = admissionCardList;
    }
}
