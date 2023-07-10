package com.praver.springboot.service.impl;

import com.praver.springboot.entity.Article;
import com.praver.springboot.mapper.ArticleMapper;
import com.praver.springboot.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author praver
 * @since 2023-06-18
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
