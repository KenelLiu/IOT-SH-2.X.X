package iot.sh.share;

public class CacheData<T> {
    private long startTime=System.currentTimeMillis();
    private T entity;

    public CacheData(T entity) {
        this.entity = entity;
    }

    public CacheData<T> setEntity(T entity) {
        this.entity = entity;
        return this;
    }

    public  T getEntity(){
        return this.entity;
    }

    public long getStartTime(){
        return this.startTime;
    }
}
