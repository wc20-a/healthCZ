package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.zookeeper.Op;

import java.util.List;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 0:31
 */
public interface CheckItemDao {
    //添加检查项
    void add(CheckItem checkItem);

    //分页查询
    Page<CheckItem> findPage(String queryString);

    //根据id值查询检查项
    CheckItem findById(int id);

    //检查项修改
    void edit(CheckItem checkItem);

    long findCountByCheckItemId (int id);

    void delete(int id);

    List<CheckItem> findAll();
}
