package com.team14.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.vo.ClothImg;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2022-06-27
 */
public interface ClothImgService extends IService<ClothImg> {

    List<ClothImg> getByClothId(Long clothId);

    JsonResponse addState(ClothImg clothImg);
}