package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.Date;

public class Discipline {
    private Integer id;

    private Integer disid;

    private String sid;

    private String place;

    private String subjects;

    private String dateof;

    private Date addtime;

    private Integer reason;

    private String note;

    private Integer totaltime;

    public Discipline(Integer id, Integer disid, String sid, String place, String subjects, String dateof, Date addtime, Integer reason, String note, Integer totaltime) {
        this.id = id;
        this.disid = disid;
        this.sid = sid;
        this.place = place;
        this.subjects = subjects;
        this.dateof = dateof;
        this.addtime = addtime;
        this.reason = reason;
        this.note = note;
        this.totaltime = totaltime;
    }

    public Discipline() {
        super();
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects == null ? null : subjects.trim();
    }

    public String getDateof() {
        return dateof;
    }

    public void setDateof(String dateof) {
        this.dateof = dateof == null ? null : dateof.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(Integer totaltime) {
        this.totaltime = totaltime;
    }
}