package com.praver.springboot.service.impl;

import com.praver.springboot.entity.Files;
import com.praver.springboot.mapper.FilesMapper;
import com.praver.springboot.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author praver
 * @since 2023-06-07
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

}
