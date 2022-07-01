package com.team14.sms.service;

import com.team14.sms.base.JsonResponse;
import com.team14.sms.dao.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
public interface ManagerService extends IService<Manager> {

    Manager getByManId(Long manId);

    JsonResponse login(Manager manager);

    JsonResponse addState(Manager manager);
}
