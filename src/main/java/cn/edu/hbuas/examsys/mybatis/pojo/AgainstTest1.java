package cn.edu.hbuas.examsys.mybatis.pojo;

import java.sql.Date;

/*author：杨枫
* 添加违纪信息用
* */
public class AgainstTest1 {
    private String sname; //学生姓名
    private String idcard; //身份证号
    private String nativePlace; //身份证号
    private String college; //学院
    private String profession; //专业
    private Integer tid; //考试任务编号
    private String name; //考试科目名称
    private Date testtime; //考试时间


    public AgainstTest1(String sname, String idcard, String college, String profession, Integer tid, String name, Date testtime) {
        this.sname = sname;
        this.idcard = idcard;
        this.college = college;
        this.profession = profession;
        this.tid = tid;
        this.name = name;
        this.testtime = testtime;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }
}