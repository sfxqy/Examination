package cn.edu.hbuas.examsys.mybatis.pojo;

public class Batch {
    private Integer bid;

    private String type;

    private String grade;

    private String bname;

    private Integer tid;

    private String pbatch;

    public Batch(Integer bid, String type, String grade, String bname, Integer tid, String pbatch) {
        this.bid = bid;
        this.type = type;
        this.grade = grade;
        this.bname = bname;
        this.tid = tid;
        this.pbatch = pbatch;
    }

    public Batch() {
        super();
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname == null ? null : bname.trim();
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getPbatch() {
        return pbatch;
    }

    public void setPbatch(String pbatch) {
        this.pbatch = pbatch == null ? null : pbatch.trim();
    }
}