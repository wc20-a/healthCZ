package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.UUID;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:26
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/findPage")

    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setMealService.findPage(queryPageBean);
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile){

        //获取资源的初始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //获取资源的后缀名
        int i = originalFilename.indexOf(".");
        String substring = originalFilename.substring(i - 1);
        //生成唯一id
        String s = UUID.randomUUID().toString();
        //形成新的文件名
        String filename = s + substring;
        try {
            //上传到七牛云上
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(),filename);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,filename);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }


}

