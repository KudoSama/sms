package com.wmj.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.ClothSize;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
public interface ClothSizeService extends IService<ClothSize> {

    List<ClothSize> getByClothId(Long clothId);

    JsonResponse addState(ClothSize clothSize);

    JsonResponse deleteState(ClothSize clothSize);
}
