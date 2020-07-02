package cn.edu.hbuas.examsys.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Examdate {
    private Integer ttid;

    private Integer tid;

    private Integer eid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date etime;

    public Examdate(Integer ttid, Integer tid, Integer eid, Date etime) {
        this.ttid = ttid;
        this.tid = tid;
        this.eid = eid;
        this.etime = etime;
    }

    public Examdate() {
        super();
    }

    public Integer getTtid() {
        return ttid;
    }

    public void setTtid(Integer ttid) {
        this.ttid = ttid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }
}