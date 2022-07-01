package com.team14.sms.service;

import com.team14.sms.base.JsonResponse;
import com.team14.sms.dao.Enable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team14.sms.dao.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-25
 */
public interface EnableService extends IService<Enable> {

    JsonResponse addState(Student student);

    Enable getByStuId(Long id);
}
