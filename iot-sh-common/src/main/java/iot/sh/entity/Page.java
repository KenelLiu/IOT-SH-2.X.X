package iot.sh.entity;

import java.io.Serializable;

/**
 * 分页信息
 * @author Kenel Liu
 * nPageIndex:第几页,默认第1页
 * nPageSize:每页显示的记录数量
 * nTotalPage:总页数
 * nCount:总记录数
 * nStartRecord:记录的起始位置
 * nMaxPageSize:每页最大显示记录数量
 */
public class Page implements Serializable {
    private int nPageIndex = 1;
    private int nPageSize = 10;
    private long nTotalPage;
    private long nCount;
    private int nStartRecord;

    private int nMaxPageSize=200;

    public  Page setnMaxPageSize(int nMaxPageSize){
        this.nMaxPageSize=nMaxPageSize;
        if(this.nMaxPageSize<0)
            this.nMaxPageSize=200;
        if(this.nPageSize>this.nMaxPageSize)
            this.nPageSize=this.nMaxPageSize;
        return this;
    }
    public int getnPageIndex() {
        return nPageIndex;
    }
    public Page setnPageIndex(int nPageIndex) {
        if(nPageIndex<=0)
            nPageIndex=1;
        this.nPageIndex = nPageIndex;
        return this;
    }
    public int getnPageSize() {
        return nPageSize;
    }
    public Page setnPageSize(int nPageSize) {
        if(nPageSize>nMaxPageSize)
            nPageSize=nMaxPageSize;
        if(nPageSize<=0)
            nPageSize=10;
        this.nPageSize = nPageSize;
        return this;
    }
    public long getnTotalPage() {
        if (nCount % nPageSize == 0) {
            nTotalPage = nCount / nPageSize;
        } else {
            nTotalPage = nCount / nPageSize + 1;
        }
        if(nTotalPage<=0)
            nTotalPage=1;
        return nTotalPage;
    }

    public long getnCount() {
        return nCount;
    }

    public void setnCount(long nCount) {
        this.nCount = nCount;
    }

    public int getnStartRecord() {
        long nTotalPage=getnTotalPage();
        if(nPageIndex>nTotalPage){
            nPageIndex=(int)nTotalPage;
        }
        nStartRecord = (nPageIndex - 1) * nPageSize;
        return nStartRecord;
    }
}
