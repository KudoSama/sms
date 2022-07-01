package com.team14.sms.mapper;

import com.team14.sms.dao.Manager;
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
public interface ManagerMapper extends BaseMapper<Manager> {

}
