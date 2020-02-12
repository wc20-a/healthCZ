package com.itheima.test;

import com.google.gson.Gson;
import com.itheima.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @Author: 汪诚
 * @Date: 2020/2/7 13:23
 */
public class QiNiuTest {

    @Test
    public void test01(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "wMUlN00lJUnzpa04l2vU-_jYF3n-QlmNJWv7wzEk";
        String secretKey = "zCKwj4-Qw-Zll_jqN4qky_eOjn_UOUFrFlHSo8Sj";
        String bucket = "wc-health";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\22165\\Desktop\\图片\\jty.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "jty.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }


    @Test
    public void Test02(){
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
//...生成上传凭证，然后准备上传
        String accessKey = "wMUlN00lJUnzpa04l2vU-_jYF3n-QlmNJWv7wzEk";
        String secretKey = "zCKwj4-Qw-Zll_jqN4qky_eOjn_UOUFrFlHSo8Sj";
        String bucket = "wc-health";
        String key = "cat.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    @Test
    public void test03(){
        QiniuUtils.deleteFileFromQiniu("583f8d4b-6e22-4c50-9dab-15b01ac67b5b1.jpg");
    }
}
