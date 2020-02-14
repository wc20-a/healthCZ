package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetMealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 汪诚
 * @Date: 2020/2/6 22:31
 * 套餐管理
 */
@Service(interfaceClass = SetMealService.class )
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private String outPutPath ="F:/IdeaProjects/healthCZ/health_mobile/src/main/webapp/pages/";//从属性文件中读取要生成的html对应的目录


    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //添加套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        Integer id = setmeal.getId();
        this.SetmealAndCheckGroupIds(id,checkgroupIds);

        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        generateMobileStaticHtml();
    }
    //生成静态页面
    public void generateMobileStaticHtml(){
        List<Setmeal> list = setMealDao.getSetmeal();
        getSerMealDetailed(list);
        getSetMeal(list);
    }

    //生成套餐详情界面
    public void getSerMealDetailed(List<Setmeal> list){
        Map map = new HashMap();
        for (Setmeal setmeal : list) {
            map.put("setmeal",setMealDao.findById(setmeal.getId()));
            getHtml("mobile_setmeal_detail.ftl","setmeal_detail_" + setmeal.getId() + ".html",map);
        }
    }
    //套餐列表静态界面
    public void getSetMeal(List<Setmeal> list){
        Map map = new HashMap();
        map.put("setmealList",list);
        getHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }
    //生成静态界面
    public void getHtml(String ftlName,String htmlName,Map map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer writer = null;
        try {
            //加载模板文件
            Template template = configuration.getTemplate(ftlName);
            writer = new FileWriter(outPutPath+htmlName);
            //输出
            template.process(map,writer);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                //关闭流
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //获取所有套餐
    @Override
    public List<Setmeal> getSetmeal() {
        return setMealDao.getSetmeal();
    }

    //通过id查找套餐
    @Override
    public Setmeal findById(int id) {
        return setMealDao.findById(id);
    }

    //设置套餐与检查组的关联关系
    public void SetmealAndCheckGroupIds(Integer SetmealId,Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("SetmealId",SetmealId);
                map.put("checkgroupId",checkgroupId);
                setMealDao.SetmealAndCheckGroupIds(map);
            }
        }
    }

}
