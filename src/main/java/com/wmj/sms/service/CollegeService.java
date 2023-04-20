package com.wmj.sms.service;

import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.College;
import com.baomidou.mybatisplus.extension.service.IService;

import jakarta.validation.Valid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
 */
public interface CollegeService extends IService<College> {

    College getByColId(Long colId);

    JsonResponse login(College college);

    JsonResponse addState(College college);

    JsonResponse resetPassword(College college);

    JsonResponse getCollegeList();

    JsonResponse modify(College college);

    JsonResponse getCollege();
}
