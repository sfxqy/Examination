package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.Date;

public class Surface {
    private Integer suid;

    private String teacher;

    private Integer eid;

    private Integer tid;

    private Integer rid;

    private Date testtime;

    private Date begin;

    private Date end;

    private String worknumber;

    private Integer number;

    public Surface(Integer suid, String teacher, Integer eid, Integer tid, Integer rid, Date testtime, Date begin, Date end, String worknumber, Integer number) {
        this.suid = suid;
        this.teacher = teacher;
        this.eid = eid;
        this.tid = tid;
        this.rid = rid;
        this.testtime = testtime;
        this.begin = begin;
        this.end = end;
        this.worknumber = worknumber;
        this.number = number;
    }

    public Surface() {
        super();
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
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

    public String getWorknumber() {
        return worknumber;
    }

    public void setWorknumber(String worknumber) {
        this.worknumber = worknumber == null ? null : worknumber.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}