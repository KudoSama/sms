package com.team14.sms.service;

import com.team14.sms.base.JsonResponse;
import com.team14.sms.vo.College;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
public interface CollegeService extends IService<College> {

    College getByColId(Long colId);

    JsonResponse login(College college);

    JsonResponse addState(College college);
}
