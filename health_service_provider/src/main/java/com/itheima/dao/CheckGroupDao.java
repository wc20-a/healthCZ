package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:34
 */
public interface CheckGroupDao {

    //添加检查组
    void add(CheckGroup checkGroup);

    //中间表中添加检查项和检查组的关联
    void addGroupItem(Map<String,Integer> map);

    //分页查询
    Page<CheckGroup> findPage(String queryString);

    //根据id查询检查组
    CheckGroup findCheckGroupById(Integer id);

    //中间表中根据检查组id值查询所有检查项的id
    List<Integer> findCheckItemsById(Integer id);

    //修改检查组基本信息
    void edit(CheckGroup checkGroup);

    //根据检查组id删除中间关联
    void deleteById(Integer id);

    //查询所有检查组
    List<CheckGroup> findAll();

    //查询套餐于检查组关联
    Integer findSetmealAndCheckGroup(Integer id);

    //删除检查组
    void deleteCheckGroupById(Integer id);
}
