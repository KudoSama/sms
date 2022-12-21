package com.wmj.sms.mapper;

import com.wmj.sms.dao.School;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2022-12-25
 */
@Mapper
@Component
public interface SchoolMapper extends BaseMapper<School> {

}
