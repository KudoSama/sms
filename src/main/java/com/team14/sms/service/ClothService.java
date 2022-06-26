package com.team14.sms.service;

import com.team14.sms.vo.Cloth;
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
}
