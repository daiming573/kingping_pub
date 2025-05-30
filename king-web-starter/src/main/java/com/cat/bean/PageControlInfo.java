package com.cat.bean;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PageControlInfo implements Serializable {

    private static final long serialVersionUID = 6597075424155909806L;

    public int start;
    public int max;
    public int totalNum;

    public List searchData;

    public PageControlInfo() {
        start = 1;
        max = 20;
        totalNum = 0;
        searchData = null;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List getSearchData() {
        return searchData;
    }

    public void setSearchData(List searchData) {
        this.searchData = searchData;
    }
}