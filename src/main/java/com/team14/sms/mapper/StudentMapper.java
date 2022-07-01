package com.team14.sms.mapper;

import com.team14.sms.dao.Student;
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
public interface StudentMapper extends BaseMapper<Student> {

}
