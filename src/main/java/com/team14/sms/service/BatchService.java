package com.team14.sms.service;

import com.team14.sms.base.JsonResponse;
import com.team14.sms.dao.Batch;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
public interface BatchService extends IService<Batch> {

    JsonResponse modifyBatch(Batch batch);

    JsonResponse addState(Batch batch);

    JsonResponse getCurBatch();

    JsonResponse getNotExamineBatch();

    Batch getByBatchId(Long batchId);
}
