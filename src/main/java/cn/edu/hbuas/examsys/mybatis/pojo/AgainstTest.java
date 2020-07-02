package cn.edu.hbuas.examsys.mybatis.pojo;

import java.sql.Date;


public class AgainstTest {
    private Integer id;  //主键
    private Integer disid; //考试任务编号
    private String sname; //学生姓名
    private String sex; //学生性别
    private Integer age; //学生年龄
    private String idcard; //身份证号
    private String nativePlace; //籍贯
    private String sid;  //学号
    private String college; //学院
    private String clazz; //专业
    private String grade; //年级
    private Date testtime; //考试时间
    private String name; //考试科目名称
    private String state;//违纪类型
    private Integer totaltime; //违纪处罚时间

    public AgainstTest(Integer id, Integer disid, String sname, String sex, String idcard,String nativePlace, String sid, String college, String clazz, String grade, Date testtime, String name, String state,Integer totaltime) {
        this.id = id;
        this.disid = disid;
        this.sname = sname;
        this.sex = sex;
        this.idcard = idcard;
        this.nativePlace=nativePlace;
        this.sid = sid;
        this.college = college;
        this.clazz = clazz;
        this.grade = grade;
        this.testtime = testtime;
        this.name = name;
        this.state = state;
        this.totaltime=totaltime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDisid() {
        return disid;
    }

    public void setDisid(Integer disid) {
        this.disid = disid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(Integer totaltime) {
        this.totaltime = totaltime;
    }
}