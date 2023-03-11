package com.wmj.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.ClothImg;
import com.wmj.sms.dto.PageDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
public interface ClothImgService extends IService<ClothImg> {

    List<ClothImg> getByClothId(Long clothId);

    JsonResponse addState(ClothImg clothImg);

    JsonResponse deleteState(ClothImg clothImg);
}
