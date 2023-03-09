package com.wmj.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Notice;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2023-3-8
 */
public interface NoticeService extends IService<Notice> {
    JsonResponse addState(Notice notice);

    JsonResponse modifyNotice(Notice notice);

    JsonResponse deleteNotice(Notice notice);

    JsonResponse getNoticeById(Notice notice);

    JsonResponse getCurNotice();

    JsonResponse getNoticeList();
}
