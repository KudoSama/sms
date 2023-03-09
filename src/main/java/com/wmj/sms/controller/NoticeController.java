package com.wmj.sms.controller;

import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Batch;
import com.wmj.sms.dao.Notice;
import com.wmj.sms.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2023-3-8
 */
@Controller
@RequestMapping("/api/notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加通知接口",notes = "应传入：startDate（时间戳, endDate（时间戳")
    public JsonResponse addNotice(@RequestBody @Valid Notice notice) {
        return noticeService.addState(notice);
    }

    @RequestMapping(value = "/modify", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改通知接口",notes = "应传入：noticeId, startDate（时间戳, endDate（时间戳")
    public JsonResponse modifyNotice(@RequestBody @Valid Notice notice) {
        return noticeService.modifyNotice(notice);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除通知接口",notes = "应传入：id")
    public  JsonResponse deleteNotice(@RequestBody @Valid Notice notice) { return noticeService.deleteNotice(notice);}

    @RequestMapping(value = "/getNoticeById", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "通过ID获取单一通知", notes = "应传入：id")
    public JsonResponse getNoticeById(@RequestBody @Valid Notice notice) {
        return noticeService.getNoticeById(notice);
    }

    @RequestMapping(value = "/getCurNotice", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获当前时间内的通知", notes = "无需传入")
    public JsonResponse getCurNotice() {
        return noticeService.getCurNotice();
    }

    @RequestMapping(value = "/getNoticeList", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取所有通知接口", notes = "无需传入")
    public JsonResponse getNoticeList() {
        return noticeService.getNoticeList();
    }
}
