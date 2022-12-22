package com.wmj.sms.service;

import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wmj.sms.dto.PageDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
 */
public interface ManagerService extends IService<Manager> {

    Manager getByManId(Long manId);

    JsonResponse login(Manager manager);

    JsonResponse addState(Manager manager);

    JsonResponse resetPassword(Manager manager);

    JsonResponse getManagerList(PageDTO pageDTO);
}
