package com.team14.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.dao.StuApply;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-27
 */
public interface StuApplyService extends IService<StuApply> {

    void exportState(HttpServletResponse response);

    void exportResult(HttpServletResponse response);

    JsonResponse apply(StuApply stuApply);

    Page<StuApply> selectNotExamineStuApply(PageDTO pageDTO);

    Page<StuApply> selectExaminedStuApply(PageDTO pageDTO);

    boolean agreeBatch(List<Long> idList);

    boolean disagreeBatch(List<StuApply> list);

    JsonResponse getStateByStu();

    JsonResponse schoolModify(StuApply stuApply);

    JsonResponse studentModify(StuApply stuApply);

    Page<StuApply> studentSelect(PageDTO pageDTO);
}
