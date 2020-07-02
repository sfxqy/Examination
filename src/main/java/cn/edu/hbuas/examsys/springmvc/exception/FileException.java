package cn.edu.hbuas.examsys.springmvc.exception;

public class FileException extends Exception{
    public FileException(){
        super();
    }

    public FileException(String msg){
        super(msg);
    }
}
