package cn.edu.hbuas.examsys.mybatis.pojo;

public class Role {
    private Integer rid;

    private String rname;

    public Role(Integer rid, String rname) {
        this.rid = rid;
        this.rname = rname;
    }

    public Role() {
        super();
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname == null ? null : rname.trim();
    }
}