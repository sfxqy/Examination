package cn.edu.hbuas.examsys.mybatis.pojo;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author SFX
 * 座位对照表
 */
public class Contrast {

    private Integer suid;

    //学院编号
    private Integer cid;

    //学院名称
    private String cname;

    //年级
    private String grade;

    //考试科目名称
    private String ename;

    //考场编号
    private Integer rid;


    private String time;


    private String place;

    private List<Details> details;


    public Contrast() {
    }

    public Contrast(Integer suid, Integer cid, String cname, String grade, String ename, Integer rid, String time, String place, List<Details> details) {
        this.suid = suid;
        this.cid = cid;
        this.cname = cname;
        this.grade = grade;
        this.ename = ename;
        this.rid = rid;
        this.time = time;
        this.place = place;
        this.details = details;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }


}
