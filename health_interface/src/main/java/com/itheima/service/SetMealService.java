package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:29
 */
public interface SetMealService {

    //分页查询
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> getSetmeal();

    Setmeal findById(int id);

}
