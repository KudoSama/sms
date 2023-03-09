package com.wmj.sms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wmj.sms.dao.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2023-3-8
 */
@Mapper
@Component
public interface NoticeMapper extends BaseMapper<Notice> {
}
