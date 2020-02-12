package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.OrderOther;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 汪诚
 * @Date: 2020/2/10 22:49
 */

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void upload(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                Integer count = orderSettingDao.count(orderSetting.getOrderDate());
                if(count > 0){
                    orderSettingDao.update(orderSetting);
                }else{
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<OrderOther> getOrderSettingByMonth(String date) {
        String begin = date+"-1";
        String end = date + "-31";
        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        return orderSettingDao.getOrderSettingByMonth(map);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        int number = orderSetting.getNumber();
        Integer count = orderSettingDao.count(orderDate);
        if(count > 0){
            orderSettingDao.update(orderSetting);
        }else{
            orderSettingDao.add(orderSetting);
        }
    }
}
