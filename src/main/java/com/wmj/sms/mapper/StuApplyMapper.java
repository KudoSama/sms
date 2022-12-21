package com.wmj.sms.mapper;

import com.wmj.sms.dao.StuApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wmj.sms.dao.CountResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
@Mapper
@Component
public interface StuApplyMapper extends BaseMapper<StuApply> {
    List<CountResult> countResult(Long batchId);

    void deleteByBatchId(Long batchId);
}
