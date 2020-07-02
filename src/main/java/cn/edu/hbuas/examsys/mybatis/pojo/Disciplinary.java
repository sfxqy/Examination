package cn.edu.hbuas.examsys.mybatis.pojo;

public class Disciplinary {
    private Integer id;

    private Integer did;

    private String type;

    private String distype;

    private Integer time;

    public Disciplinary(Integer id, Integer did, String type, String distype, Integer time) {
        this.id = id;
        this.did = did;
        this.type = type;
        this.distype = distype;
        this.time = time;
    }

    public Disciplinary() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDistype() {
        return distype;
    }

    public void setDistype(String distype) {
        this.distype = distype == null ? null : distype.trim();
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}