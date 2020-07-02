package cn.edu.hbuas.examsys.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author SFX
 */

@Component
public class ContextContainer {

    private HashMap<String,Object> hashMap;

    public ContextContainer(){
        hashMap = new HashMap<>();
    }

    public void addToContainer(String key,Object data){
        hashMap.put(key,data);
    }

    public Object removeFromContainer(String key){
        return hashMap.remove(key);
    }

    public Object getData(String key){
        return hashMap.get(key);
    }

    public boolean isExist(String key){
        return hashMap.containsKey(key);
    }
}
