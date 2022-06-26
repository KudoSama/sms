package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.vo.Batch;
import com.team14.sms.mapper.BatchMapper;
import com.team14.sms.service.BatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.vo.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@Service
public class BatchServiceImpl extends ServiceImpl<BatchMapper, Batch> implements BatchService {

    @Autowired
    private BatchService batchService;

    @Override
    public boolean updateByBatch(Batch batch) {
        QueryWrapper<Batch> wrapper =new QueryWrapper<>();
        wrapper.eq("batch_id", batch.getBatchId());
        Batch temp = super.getOne(wrapper);
        if (temp == null) {
            return false;
        }
        temp.setBatchDatestart(batch.getBatchDatestart());
        temp.setBatchDateend(batch.getBatchDateend());
        batchService.update(temp, wrapper);
        return true;
    }
}
