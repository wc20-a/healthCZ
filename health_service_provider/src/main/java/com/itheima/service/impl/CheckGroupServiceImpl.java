package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:31
 */
@Service(interfaceClass = CheckGroupService.class )
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组基础数据
        checkGroupDao.add(checkGroup);
        //添加检查项于检查组的关联,中间表
        this.addGroupItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //使用分页插件
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findCheckGroupById(Integer id) {
        return checkGroupDao.findCheckGroupById(id);
    }

    @Override
    public List<Integer> findCheckItemsById(Integer id) {
        return checkGroupDao.findCheckItemsById(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.edit(checkGroup);
        Integer id = checkGroup.getId();
        checkGroupDao.deleteById(id);
        this.addGroupItem(id,checkitemIds);

    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    @Override
    public void deleteCheckgroupById(Integer id) {
        int count = checkGroupDao.findSetmealAndCheckGroup(id);
        if(count > 0){
            new RuntimeException();
        }
        //删除中间关联表
        checkGroupDao.deleteById(id);
        //删除检查组
        checkGroupDao.deleteCheckGroupById(id);
    }

    //检查组和检查项的关联
    public void addGroupItem( Integer checkGroupId , Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.addGroupItem(map);
            }
        }
    }

}
