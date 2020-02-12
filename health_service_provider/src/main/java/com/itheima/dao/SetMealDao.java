package com.itheima.dao;

import com.github.pagehelper.Page;

import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:34
 */
public interface SetMealDao {


    //分页查询
    Page<Setmeal> findPage(String queryString);

    //添加套餐
    void add(Setmeal setmeal);

    //建立套餐于检查组之间的关系
    void SetmealAndCheckGroupIds(Map<String,Integer> map);

    //获取套餐
    List<Setmeal> getSetmeal();

    //根据id值查询套餐
    Setmeal findById(int id);
}
