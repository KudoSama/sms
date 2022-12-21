package com.wmj.sms.service;

import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Batch;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-26
 */
public interface BatchService extends IService<Batch> {

    JsonResponse modifyBatch(Batch batch);

    JsonResponse addState(Batch batch);

    JsonResponse getCurBatch();

    JsonResponse getNotExamineBatch();

    Batch getByBatchId(Long batchId);

    JsonResponse deleteBatch(Batch batch);

    JsonResponse getBatchList();
}
