package com.team14.sms.mapper;

import com.team14.sms.dao.Cloth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@Mapper
@Component
public interface ClothMapper extends BaseMapper<Cloth> {

}
