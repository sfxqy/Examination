package cn.edu.hbuas.examsys.mybatis.pojo;

public class StudentKey {
    private String sid;

    private Integer tid;

    public StudentKey(String sid, Integer tid) {
        this.sid = sid;
        this.tid = tid;
    }

    public StudentKey() {
        super();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}