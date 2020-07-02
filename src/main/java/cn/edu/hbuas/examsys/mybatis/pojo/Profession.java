package cn.edu.hbuas.examsys.mybatis.pojo;

public class Profession extends ReadFileFather{
    private Integer id;

    private Integer pid;

    private Integer cid;

    private String pname;

    public Profession(Integer id, Integer pid, Integer cid, String pname) {
        this.id = id;
        this.pid = pid;
        this.cid = cid;
        this.pname = pname;
    }

    public Profession() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }
}