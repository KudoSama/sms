package com.wmj.sms.service;

import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.School;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-25
 */
public interface SchoolService extends IService<School> {

    JsonResponse login(School school);

    JsonResponse setEmail(int num);

    JsonResponse resetPassword(int inNum, int num);

    JsonResponse modify(School school, int email, int num);

    JsonResponse getSchool();

    JsonResponse setEmailById(String email, int num);

    JsonResponse getSchoolById(School school);
}
