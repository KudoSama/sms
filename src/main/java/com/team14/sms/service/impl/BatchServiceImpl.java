package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.BatchMapper;
import com.team14.sms.service.BatchService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.dao.Batch;
import com.team14.sms.dao.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public JsonResponse modifyBatch(Batch batch) {
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
                    QueryWrapper<Batch> wrapper = new QueryWrapper<>();
                    wrapper.eq("batch_id", batch.getBatchId());
                    if (super.getOne(wrapper) != null) {
                        return JsonResponse.failure(batch.getBatchId() + "批次已经存在，请填写新的批次号");
                    }
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
    public JsonResponse getNotExamineBatch() {
        Date curDate = new Date();
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();

        // 查找结束时间早于等于今天，且今天早于等于批次结束日期的下个月
        wrapper.le("batch_dateEnd", curDate);//.ge("batch_deteEnd", nextMonth)
        List<Batch> notExamineBatchList = super.list(wrapper);// 查询到比当前时间早结束的批次列表

        Batch notExamineBatch = null;
        Date notExamineDate;
        for (Batch batch: notExamineBatchList) {
            Calendar temp = Calendar.getInstance();
            temp.setTime(batch.getBatchDateend());
            temp.add(Calendar.MONTH, 1); // 获取批次结束后一个月的时间
            notExamineDate = temp.getTime();

            if (notExamineDate.after(curDate)) {
                notExamineBatch = batch;
                break;
            }
        }

        if (notExamineBatch == null) {
            return JsonResponse.failure("当前不属于审批阶段");
        }
        return JsonResponse.success(notExamineBatch, "查询成功");
    }

    @Override
    public Batch getByBatchId (Long batchId) {
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();
        wrapper.eq("batch_id", batchId);
        return super.getOne(wrapper);
    }
}
