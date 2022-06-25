package com.team14.sms.mapper;

import com.team14.sms.vo.College;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
@Mapper
@Component
public interface CollegeMapper extends BaseMapper<College> {

}
