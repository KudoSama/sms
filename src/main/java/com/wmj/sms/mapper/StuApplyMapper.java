package com.wmj.sms.mapper;

import com.wmj.sms.dao.CountResultByManager;
import com.wmj.sms.dao.CountResultByStudent;
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

    List<CountResultByManager> countResultByManager(Long batchId, Long colId);

    List<CountResultByStudent> countResultByStudent(Long batchId, Long manId);

    void deleteByBatchId(Long batchId);

    void deleteByClothId(Long clothId);
}
