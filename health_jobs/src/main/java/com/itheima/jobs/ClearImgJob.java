package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Author: 汪诚
 * @Date: 2020/2/10 17:55
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public  void clearImg(){

        //获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if(set != null){
            for (String picName : set) {
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                System.out.println(picName);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
            }

        }

    }
}
