package com.praver.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.praver.springboot.entity.Files;

import com.praver.springboot.service.IFilesService;
import com.praver.springboot.util.Constants;
import com.praver.springboot.util.Result;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    IFilesService service;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 文件上传接口
     *
     * @param file 前端上传文件
     * @return 文件的下载地址
     * @throws IOException
     */

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //定义一个标准码，防止文件名相同
        String uuid = IdUtil.fastSimpleUUID();
        String fileName = uuid + StrUtil.DOT + type;
        File lastFile = new File(fileUploadPath + fileName);

        //判断文件目录是否存在，若无则创建
        if (!lastFile.getParentFile().exists()) {
            lastFile.getParentFile().mkdir();
        }
        String url;
        //上传文件到磁盘
        file.transferTo(lastFile);
        //获取文件的md5
        String md5 = SecureUtil.md5(lastFile);
        //从数据表中查询是否存在相同记录
        Files dbfiles = getFilesByMd5(md5);
        if (dbfiles != null) {
            url = dbfiles.getUrl();
            //删除已存在文件
            lastFile.delete();
        } else {
            url = "http://localhost:9090/api/file/" + fileName;
        }

        //数据存储到数据库
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize(size / 1024);
        saveFiles.setUrl(url);
        saveFiles.setMd5(md5);
        service.save(saveFiles);
        //清除缓存
//        flushRedis(Constants.FILES_KEY);
        return url;
    }

    /**
     * 文件下载接口
     *
     * @param fileName
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码读取文件
        File lastFile = new File(fileUploadPath + fileName);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        os.write(FileUtil.readBytes(lastFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5来查询文件
     *
     * @param md5
     * @return files
     */
    private Files getFilesByMd5(String md5) {
        //查询
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = service.list(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    //批量删除
    @DeleteMapping("/deleteList/{ids}")
    public Result deleteList(@PathVariable Integer[] ids) {
        List<Files> files = service.listByIds(Arrays.asList(ids));
        for (Files file : files) {
            file.setIsDelete(true);
            service.saveOrUpdate(file);
        }
        return Result.success();
    }

    //根据id删除
//    @CachePut(value = "files", key = "'frontAll'")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = service.getById(id);
        files.setIsDelete(true);
        service.saveOrUpdate(files);
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable String id) {
        return Result.success(service.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name
    ) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", false);
        if (!"".equals(name)) {
            queryWrapper.like("username", name);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(service.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    // 新增或修改
//    @CachePut(value = "files", key = "'frontAll'")
    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
//        flushRedis(Constants.FILES_KEY);
        return Result.success(service.saveOrUpdate(files));
    }


    //删除缓存
//    private void flushRedis(String key) {
//        stringRedisTemplate.delete(key);
//    }


}
