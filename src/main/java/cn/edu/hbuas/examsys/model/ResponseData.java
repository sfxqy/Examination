package cn.edu.hbuas.examsys.model;

import cn.edu.hbuas.examsys.mybatis.pojo.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpSession;

@Data
@Setter
@Getter
public class ResponseData {

    private boolean status;
    private Integer code;
    private String msg;
    private Object data;
    private Long total;
    private Object rows;
    private Customer customer;



    public ResponseData(Object rows, boolean status){
        this.msg=msg;
        this.status=status;
    }

    public ResponseData(String msg, boolean status){
        this.msg=msg;
        this.status=status;
    }

    public ResponseData(String msg,boolean status,Long total,Object rows){
        this.msg=msg;
        this.status=status;
        this.total=total;
        this.rows=rows;
    }

    public ResponseData(String msg,Long total,Object rows){
        this.status = true;
        this.msg = msg;
        this.total = total;
        this.rows = rows;
    }


    public ResponseData(Customer customer,String msg,boolean status){
        this.customer=customer;
        this.msg=msg;
        this.status=status;
    }
}
