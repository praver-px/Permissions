package com.praver.springboot.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.praver.springboot.util.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.praver.springboot.util.Result;
import com.praver.springboot.service.IArticleService;
import com.praver.springboot.entity.Article;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author praver
 * @since 2023-06-18
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    // 新增或修改
    @PostMapping
    public Result save(@RequestBody Article article) {
        if (article.getId() == null){
            article.setTime(DateUtil.now());
            article.setUser(TokenUtils.getCurrentUser().getNickname());
        }
        return Result.success(articleService.saveOrUpdate(article));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(articleService.removeById(id));
    }

    // 查询所有
    @GetMapping
    public Result findAll() {
        return Result.success(articleService.list());
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(articleService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(articleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    //批量删除
    @PostMapping("/deleteList")
    public Result deleteList(@RequestBody List<Integer> ids) {
        return Result.success(articleService.removeByIds(ids));
    }

}

