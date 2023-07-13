package iot.sh.entity;

import java.io.Serializable;

/**
 * @author Kenel Liu
 */
public abstract class BaseModel implements Serializable {
    private Page page;
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
}
