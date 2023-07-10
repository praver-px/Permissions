package com.praver.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.praver.springboot.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author praver
 * @since 2023-06-07
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
