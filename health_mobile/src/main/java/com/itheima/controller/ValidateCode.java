package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @Author: 汪诚
 * @Date: 2020/2/15 0:06
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCode {

    @Autowired
    private JedisPool jedisPool;

    //发送验证码
    public Result send4Order(String telephone){
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
            String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
            jedisPool.getResource().setex(key,300,validateCode.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);

        }
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
