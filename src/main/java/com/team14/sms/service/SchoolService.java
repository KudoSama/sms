package com.team14.sms.service;

import com.team14.sms.base.JsonResponse;
import com.team14.sms.vo.School;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-25
 */
public interface SchoolService extends IService<School> {

    JsonResponse login(School school);
}
