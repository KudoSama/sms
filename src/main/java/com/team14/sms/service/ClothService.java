package com.team14.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.dao.Cloth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
public interface ClothService extends IService<Cloth> {

    Cloth getByClothId(Long clothId);

    Page<Cloth> getByGender(PageDTO pageDTO);

    Page<Cloth> getByBatchId(Long batchId, PageDTO pageDTO);

    JsonResponse addState(Cloth cloth);
}
