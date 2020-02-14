package com.itheima.test;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.utils.SMSUtils;

/**
 * @Author: 汪诚
 * @Date: 2020/2/14 13:19
 */
public class SMSTest {
    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"18298297347","5408");
    }
}
