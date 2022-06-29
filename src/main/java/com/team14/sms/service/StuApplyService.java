package com.team14.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.vo.StuApply;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-27
 */
public interface StuApplyService extends IService<StuApply> {

    JsonResponse apply(StuApply stuApply);

    Page<StuApply> selectStuApply(PageDTO pageDTO);

}
