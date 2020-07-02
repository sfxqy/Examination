package cn.edu.hbuas.examsys.model;


import lombok.Builder;
import lombok.Data;

/**
 * @author SFX
 * 将用户信息封装存放到session中
 */

@Data
public class User {

    //用户id
    private Integer id;

    //用户名
    private String account;

    //用户所在学院编号 -1代表超级管理员  0表示教务处管理员  其他编号对应各院院别编号
    private Integer cid;

    public User() {
    }

    public User(Integer id, String account, Integer cid) {
        this.id = id;
        this.account = account;
        this.cid = cid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
