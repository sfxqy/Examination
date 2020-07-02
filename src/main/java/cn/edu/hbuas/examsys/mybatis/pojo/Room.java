package cn.edu.hbuas.examsys.mybatis.pojo;

public class Room extends ReadFileFather{
    private Integer rrid;

    private Integer tid;

    private Integer rid;

    private String place;

    private String number;

    private Integer campus;

    private Integer storid;

    public Room(Integer rrid, Integer tid, Integer rid, String place, String number, Integer campus, Integer storid) {
        this.rrid = rrid;
        this.tid = tid;
        this.rid = rid;
        this.place = place;
        this.number = number;
        this.campus = campus;
        this.storid = storid;
    }

    public Room() {
        super();
    }

    public Integer getRrid() {
        return rrid;
    }

    public void setRrid(Integer rrid) {
        this.rrid = rrid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public Integer getStorid() {
        return storid;
    }

    public void setStorid(Integer storid) {
        this.storid = storid;
    }
}