package cn.edu.hbuas.examsys.mybatis.pojo;

public class DegreeKey {
    private Integer sid;

    private Integer tid;

    public DegreeKey(Integer sid, Integer tid) {
        this.sid = sid;
        this.tid = tid;
    }

    public DegreeKey() {
        super();
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}