package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.Date;

public class Details {
    private Integer did;

    private Integer suid;

    private String sid;

    private String sname;

    private String sex;

    private String pname;

    private Integer tid;

    private String name;

    private Integer eid;

    private Date examtime;

    private String place;

    private Date begin;

    private Date end;

    private String time;

    private String seat;

    private Integer rid;

    public Details(Integer did, Integer suid, String sid, String sname, String sex, String pname, Integer tid, String name, Integer eid, Date examtime, String place, Date begin, Date end, String time, String seat, Integer rid) {
        this.did = did;
        this.suid = suid;
        this.sid = sid;
        this.sname = sname;
        this.sex = sex;
        this.pname = pname;
        this.tid = tid;
        this.name = name;
        this.eid = eid;
        this.examtime = examtime;
        this.place = place;
        this.begin = begin;
        this.end = end;
        this.time = time;
        this.seat = seat;
        this.rid = rid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Details() {
        super();
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
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
        this.name = name == null ? null : name.trim();
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getExamtime() {
        return examtime;
    }

    public void setExamtime(Date examtime) {
        this.examtime = examtime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat == null ? null : seat.trim();
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}