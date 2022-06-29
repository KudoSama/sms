package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.BatchMapper;
import com.team14.sms.service.BatchService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Batch;
import com.team14.sms.vo.User;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public JsonResponse modifyByBatch(Batch batch) {
        QueryWrapper<Batch> wrapper =new QueryWrapper<>();
        wrapper.eq("batch_id", batch.getBatchId());
        User loginUser = SessionUtils.getCurUser();
        // System.out.println(loginUser);
        // 只有学校用户才能修改批次
        if (loginUser.getUserType().equals("1")) {
            // 未填写开始时间或结束时间
            if (batch.getBatchDateend() == null || batch.getBatchDatestart() == null) {
                return JsonResponse.failure("修改失败，您未填写开始日期或结束时间");
            } else {
                // 结束时间小于开始时间
                if (batch.getBatchDateend().compareTo(batch.getBatchDatestart()) <= 0) {
                    return JsonResponse.failure("修改失败，您填写的结束日期早于开始日期");
                } else {
                    Batch temp = super.getOne(wrapper);
                    if (temp == null) {
                        return JsonResponse.failure("修改失败，不存在该批次");
                    }
                    temp.setBatchDatestart(batch.getBatchDatestart());
                    temp.setBatchDateend(batch.getBatchDateend());
                    super.update(temp, wrapper);
                    return JsonResponse.successMessage("修改成功");
                }
            }
        }
        return JsonResponse.failure("修改失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse addState(Batch batch) {
        User loginUser = SessionUtils.getCurUser();
        // System.out.println(loginUser);
        // 只有学校用户才能添加批次
        if (loginUser.getUserType().equals("1")) {
            try {
                // 未填写批次号、开始时间或结束时间
                if (batch.getBatchId() == null || batch.getBatchDateend() == null ||
                        batch.getBatchDatestart() == null) {
                    return JsonResponse.failure("添加失败，您未填写批次号、开始日期或结束时间");
                } else {
                    // 结束时间小于开始时间
                    if (batch.getBatchDateend().compareTo(batch.getBatchDatestart()) <= 0 ) {
                        return JsonResponse.failure("添加失败，您填写的结束日期早于开始日期");
                    } else {
                        super.save(batch);
                        return JsonResponse.successMessage("添加成功");
                    }
                }
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse getCurBatch() {
        Date curDate = new Date();
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();
        wrapper.le("batch_dateStart", curDate).ge("batch_dateEnd", curDate);
        Batch curBatch = super.getOne(wrapper);
        if (curBatch == null) {
            return JsonResponse.failure("当前不属于申请阶段");
        }
        return JsonResponse.success(curBatch, "查询成功");
    }

    @Override
    public Batch getByBatchId (Long batchId) {
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();
        wrapper.eq("batch_id", batchId);
        return super.getOne(wrapper);
    }
}
