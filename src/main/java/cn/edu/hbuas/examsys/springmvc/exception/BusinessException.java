package cn.edu.hbuas.examsys.springmvc.exception;

public class BusinessException extends Exception {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BusinessException(){
        super();
    }

    public BusinessException(String msg){
        super(msg);
    }
}
