package cn.edu.hbuas.examsys.mybatis.pojo;

public class Customer {
    private Integer id;

    private String account;

    private String password;

    private Integer cid;

    public Customer(Integer id, String account, String password, Integer cid) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.cid = cid;
    }

    public Customer() {
        super();
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
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}