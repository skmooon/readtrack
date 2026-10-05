package com.sast.readtrack.common;

import java.util.List;

public class PageResult<T> {

    private List<T> list;
    private Integer total;
    private Integer page;
    private Integer size;

    public PageResult() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public PageResult(List<T> list, Integer total, Integer page, Integer size) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.size = size;
    }
}
