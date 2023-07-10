package com.praver.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.praver.springboot.config.AuthAccess;
import com.praver.springboot.entity.Files;
import com.praver.springboot.entity.User;
import com.praver.springboot.service.IFilesService;
import com.praver.springboot.service.IUserService;
import com.praver.springboot.util.Constants;
import com.praver.springboot.util.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    private IUserService service;

    @Resource
    private IFilesService filesService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/members")
    public Result members() {
        List<User> list = service.list();
        int qua1 = 0;
        int qua2 = 0;
        int qua3 = 0;
        int qua4 = 0;
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1:
                    qua1 += 1;
                    break;
                case Q2:
                    qua2 += 1;
                    break;
                case Q3:
                    qua3 += 1;
                    break;
                case Q4:
                    qua4 += 1;
                    break;
                default:
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(qua1, qua2, qua3, qua4));
    }

    @AuthAccess
    @GetMapping("/file/front/all")
//    @Cacheable(value = "files", key = "'frontAll'")
    public Result frontAll() {
//        //1.从缓存中查找数据
//        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
        List<Files> files;
//        if (StrUtil.isBlankIfStr(jsonStr)) {//判断缓存中是否存在数据
//            //从数据库中获取数据
        files = filesService.list();
//            //将数据库中取出的数据存入缓存中
//            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(files));
//        } else {
//            //缓存中存在数据，直接获取
//            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
//            }, true);
//        }
        return Result.success(files);
    }
}
