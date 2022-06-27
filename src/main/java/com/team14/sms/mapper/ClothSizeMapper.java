package com.team14.sms.mapper;

import com.team14.sms.vo.ClothSize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-06-27
 */
@Mapper
@Component
public interface ClothSizeMapper extends BaseMapper<ClothSize> {

}