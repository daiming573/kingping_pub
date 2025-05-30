package com.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.ObjectUtils;

import java.util.List;

@ApiModel(description = "列表数据返回内容")
public class ListData<T> {
    @ApiModelProperty("列表数据")
    private List<T> list;
    @ApiModelProperty("列表数据总数")
    private int total;

    public ListData() {
    }

    public ListData(List<T> list) {
        this.list = list;
        if (!ObjectUtils.isEmpty(list)) {
            this.total = list.size();
        } else {
            this.total = 0;
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
