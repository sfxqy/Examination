package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.List;

/**
 * @author SFX
 * 准考证实体信息类
 */
public class AdmissionCard {

    //学院名称
    private String college;
    //学号
    private String sid;

    //姓名
    private String name;

    //性别
    private char sex;

    //班级
    private String clazz;

    //座位
    private String seat;

    //学生照片
    private String photo;


    private String ename;


    private List<Details> details;

    public AdmissionCard() {
    }

    public AdmissionCard(String college, String sid, String name, char sex, String clazz, String seat, String photo, String ename, List<Details> details) {
        this.college = college;
        this.sid = sid;
        this.name = name;
        this.sex = sex;
        this.clazz = clazz;
        this.seat = seat;
        this.photo = photo;
        this.ename = ename;
        this.details = details;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
