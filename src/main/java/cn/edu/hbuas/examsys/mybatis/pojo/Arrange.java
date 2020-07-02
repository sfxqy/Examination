package cn.edu.hbuas.examsys.mybatis.pojo;

public class Arrange extends ReadFileFather{
    private Integer aid;

    private Integer tid;

    private String sid;

    private Integer eid;

    private Integer reset;

    private String aname;

    public Arrange(Integer aid, Integer tid, String sid, Integer eid, Integer reset, String aname) {
        this.aid = aid;
        this.tid = tid;
        this.sid = sid;
        this.eid = eid;
        this.reset = reset;
        this.aname = aname;
    }

    public Arrange() {
        super();
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getReset() {
        return reset;
    }

    public void setReset(Integer reset) {
        this.reset = reset;
    }
}