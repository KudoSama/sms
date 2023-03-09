package com.wmj.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.dao.Cloth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-26
 */
public interface ClothService extends IService<Cloth> {

    Cloth getByClothId(Long clothId);

    Page<Cloth> getByGender( PageDTO pageDTO);

    Page<Cloth> getByBatchId(Long batchId, PageDTO pageDTO);

    JsonResponse addState(Cloth cloth);

    JsonResponse schoolGetClothByGender(String gender, PageDTO pageDTO);

    JsonResponse modifyState(Cloth cloth);
}
