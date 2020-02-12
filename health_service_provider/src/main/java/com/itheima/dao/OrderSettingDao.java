package com.itheima.dao;

import com.itheima.entity.OrderOther;
import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 汪诚
 * @Date: 2020/2/10 23:03
 */
public interface OrderSettingDao {

    //添加预约
    public void add(OrderSetting orderSetting);

    //修改预约
    public void update(OrderSetting orderSetting);

    //根据日期查询预约
    public Integer count(Date date);

    //查询预约
    public List<OrderOther> getOrderSettingByMonth(Map<String,String> map);
}
