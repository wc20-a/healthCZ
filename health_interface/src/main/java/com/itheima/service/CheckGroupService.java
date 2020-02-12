package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:29
 */
public interface CheckGroupService {

    //添加检查项
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //分页查询
    PageResult findPage(QueryPageBean queryPageBean);

    //根据id查询检查组
    CheckGroup findCheckGroupById(Integer id);

    //中间表中根据检查组id值查询所有检查项的id
    List<Integer> findCheckItemsById(Integer id);

    //修改检查组
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    //查询所有检查组
    List<CheckGroup> findAll();

    //删除检查组
    void deleteCheckgroupById(Integer id);

}
