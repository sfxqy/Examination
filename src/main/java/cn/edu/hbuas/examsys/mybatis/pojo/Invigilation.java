package cn.edu.hbuas.examsys.mybatis.pojo;

public class Invigilation {
    private Integer id;

    private Integer tid;

    private Integer cid;

    private String number;

    public Invigilation(Integer id, Integer tid, Integer cid, String number) {
        this.id = id;
        this.tid = tid;
        this.cid = cid;
        this.number = number;
    }

    public Invigilation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }
}