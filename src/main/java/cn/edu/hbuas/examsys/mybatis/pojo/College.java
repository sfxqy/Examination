package cn.edu.hbuas.examsys.mybatis.pojo;

public class College {
    private Integer id;

    private Integer cid;

    private String college;

    public College(Integer id, Integer cid, String college) {
        this.id = id;
        this.cid = cid;
        this.college = college;
    }

    public College() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }
}