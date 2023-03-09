package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Notice;
import com.wmj.sms.dao.User;
import com.wmj.sms.mapper.NoticeMapper;
import com.wmj.sms.service.NoticeService;
import com.wmj.sms.utls.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2023-3-8
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public JsonResponse addState(Notice notice) {
        User loginUser = SessionUtils.getCurUser();
        // 只有学校用户才能添加公告
        if (loginUser.getUserType().equals("1")) {
            try {
                // 未填写批次号、开始时间或结束时间
                if (notice.getStartDate() == null || notice.getEndDate() == null) {
                    return JsonResponse.failure("添加失败，您未填写开始日期或结束时间");
                } else {
                    QueryWrapper<Notice> wrapper = new QueryWrapper<>();
                    // 结束时间小于开始时间
                    if (notice.getEndDate().compareTo(notice.getStartDate()) <= 0 ) {
                        return JsonResponse.failure("添加失败，您填写的结束日期早于开始日期");
                    } else {
                        super.save(notice);
                        return JsonResponse.successMessage("添加成功");
                    }
                }
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse modifyNotice(Notice notice) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("id", notice.getId());
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            // 未输入开始时间或结束时间
            if (notice.getStartDate() == null || notice.getEndDate() == null) {
                return JsonResponse.failure("修改失败，您未填写开始日期或结束时间");
            } else {
                // 结束时间小于开始时间
                if (notice.getEndDate().compareTo(notice.getStartDate()) <= 0) {
                    return JsonResponse.failure("修改失败，您填写的结束日期早于开始日期");
                } else {
                    Notice temp = super.getOne(wrapper);
                    if (temp == null) {
                        return JsonResponse.failure("修改失败，不存在该通知");
                    }
                    temp.setStartDate(notice.getStartDate());
                    temp.setEndDate(notice.getEndDate());
                    super.update(temp, wrapper);
                    return JsonResponse.successMessage("修改成功");
                }
            }
        }
        return JsonResponse.failure("修改失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse deleteNotice(Notice notice) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("id", notice.getId());
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            super.remove(wrapper);
            return JsonResponse.successMessage("删除成功");
        }
        return JsonResponse.failure("删除失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse getNoticeById(Notice notice) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("id", notice.getId());
        Notice temp = super.getOne(wrapper);
        if (temp == null) {
            return JsonResponse.failure("查询失败，不存在该通知！");
        }
        return JsonResponse.success(temp, "查询成功");
    }

    @Override
    public JsonResponse getCurNotice() {
        Date curDate = new Date();
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.le("start_date", curDate).ge("end_date", curDate);
        List<Notice> noticeList = noticeMapper.selectList(wrapper);
        return JsonResponse.success(noticeList, "查询成功");
    }

    @Override
    public JsonResponse getNoticeList() {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            QueryWrapper<Notice> wrapper = new QueryWrapper<>();
            List<Notice> noticeList = noticeMapper.selectList(wrapper);
            return JsonResponse.success(noticeList, "查询成功");
        }
        return JsonResponse.failure("查询失败，您无本操作权限，请联系系统管理员");
    }
}
