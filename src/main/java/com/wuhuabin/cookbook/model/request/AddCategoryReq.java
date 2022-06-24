package com.wuhuabin.cookbook.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCategoryReq {

    @Size(min = 2, max = 8)
    @NotNull(message="name参数不能为null")
    private String name;//目录名

    @Max(2)
    @NotNull(message="type参数不能为null")
    private Integer type;//目录级别

    @NotNull(message="parentId参数不能为null")
    private Integer parentId;//上一级目录的id

    @NotNull(message="orderNum参数不能为null")
    private Integer orderNum;//同级目录的排序顺序

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "AddCategoryReq{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
