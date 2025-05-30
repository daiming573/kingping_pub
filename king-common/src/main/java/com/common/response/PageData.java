package com.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author daiming5
 */
@ApiModel(description = "分页数据返回内容")
public class PageData<T> {
    @ApiModelProperty("当前页码")
    private Integer pageNo;
    @ApiModelProperty("每页大小")
    private Integer pageSize;
    @ApiModelProperty("总页数")
    private Long totalPage;
    @ApiModelProperty("列表数据")
    private List<T> list;
    @ApiModelProperty("列表数据总数")
    private long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }
}
