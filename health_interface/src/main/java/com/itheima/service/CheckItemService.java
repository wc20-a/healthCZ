package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 0:20
 */
public interface CheckItemService {
    //添加检查项
    public void add(CheckItem checkItem);

    //查询检查项
    public PageResult findPage(QueryPageBean queryPageBean);

    //检查项修改
    public void edit(CheckItem checkItem);

    //根据id值查询检查项
    public CheckItem findById(Integer id);

    void delete(Integer id);

    List<CheckItem> findAll();

}