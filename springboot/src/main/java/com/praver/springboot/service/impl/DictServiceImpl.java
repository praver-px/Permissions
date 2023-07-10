package com.praver.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.praver.springboot.entity.Dict;
import com.praver.springboot.entity.Files;
import com.praver.springboot.mapper.DictMapper;
import com.praver.springboot.mapper.FilesMapper;
import com.praver.springboot.service.IDictService;
import com.praver.springboot.service.IFilesService;
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
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
