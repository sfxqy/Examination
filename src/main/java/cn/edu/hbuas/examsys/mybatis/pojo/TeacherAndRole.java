package cn.edu.hbuas.examsys.mybatis.pojo;

import io.swagger.models.auth.In;


public class TeacherAndRole {
    private Integer tid;

    private Integer cid;

    private Integer rid;

    private String number;

    private String tname;

    private String college;

    private String wages;

    private String rname;

    private String phone;

    private String sex;

    private Integer times;

    public TeacherAndRole(Integer tid, Integer cid, Integer rid, String number, String tname,Integer times, String sex ,String college, String wages, String rname, String phone) {
        this.tid = tid;
        this.cid = cid;
        this.rid = rid;
        this.number = number;
        this.tname = tname;
        this.college = college;
        this.wages = wages;
        this.rname = rname;
        this.phone = phone;
        this.sex = sex;
        this.times = times;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}