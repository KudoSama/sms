package com.wmj.sms.service;

import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Enable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmj.sms.dao.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-25
 */
public interface EnableService extends IService<Enable> {

    JsonResponse addState(Student student);

    Enable getByStuId(Long id);

    JsonResponse delete(Student student);

    JsonResponse getEnableList();
}
