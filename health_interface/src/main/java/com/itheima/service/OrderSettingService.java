package com.itheima.service;

import com.itheima.entity.OrderOther;
import com.itheima.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: 汪诚
 * @Date: 2020/2/10 22:40
 */
public interface OrderSettingService {

    public void upload(List<OrderSetting> list);

    public List<OrderOther> getOrderSettingByMonth(String date);

    public void editNumberByDate(OrderSetting orderSetting);
}
