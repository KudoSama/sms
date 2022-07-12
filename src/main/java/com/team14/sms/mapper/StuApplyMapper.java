package com.team14.sms.mapper;

import com.team14.sms.dao.StuApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team14.sms.dao.CountResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2022-06-27
 */
@Mapper
@Component
public interface StuApplyMapper extends BaseMapper<StuApply> {
    List<CountResult> countResult(Long batchId);
}
