package com.team14.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.vo.ClothSize;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2022-06-27
 */
public interface ClothSizeService extends IService<ClothSize> {

    List<ClothSize> getByClothId(Long clothId);

    JsonResponse addState(ClothSize clothSize);
}