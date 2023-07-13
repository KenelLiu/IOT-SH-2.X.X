package iot.sh.share;

import java.util.HashMap;
import java.util.Map;

public class ShareManager {
    private static ShareManager instance=new ShareManager();

    private Map<String,CacheData>  cacheMap=new HashMap<String,CacheData>();


    private ShareManager(){}

    public static ShareManager getInstance(){
        return instance;
    }

    public ShareManager put(String key,CacheData cacheData){
        cacheMap.put(key,cacheData);
        return instance;
    }

    public CacheData<?> get(String key){
        return cacheMap.get(key);
    }

    public  CacheData<?> remove(String key){
        return cacheMap.remove(key);
    }

    public boolean containsKey(String key){
        return cacheMap.containsKey(key);
    }

}
