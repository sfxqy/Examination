package cn.edu.hbuas.examsys.mybatis.pojo;

public class Ticket {
    private Integer tid;

    private String sid;

    private String ticket;

    public Ticket(Integer tid, String sid, String ticket) {
        this.tid = tid;
        this.sid = sid;
        this.ticket = ticket;
    }

    public Ticket() {
        super();
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }
}