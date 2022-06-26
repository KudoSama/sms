package com.team14.sms.controller;


import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.BatchMapper;
import com.team14.sms.service.BatchService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Batch;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@Controller
@RequestMapping("/api/batch")
public class BatchController extends BaseController {

    @Autowired
    private BatchService batchService;

    @Autowired
    private BatchMapper batchMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加批次接口",notes = "应传入：batchId, batchDatestart（时间戳, batchDateend（时间戳")
    public JsonResponse addBatch(@RequestBody @Valid Batch batch) {
        User loginUser = SessionUtils.getCurUser();
        // System.out.println(loginUser);
        // 只有学校用户才能添加批次
        if (loginUser.getUserType().equals("4")) {
            try {
                // 结束时间小于开始时间
                if (batch.getBatchDateend().compareTo(batch.getBatchDatestart()) <= 0 ) {
                    return JsonResponse.failure("添加失败，您填写的结束日期早于开始日期");
                } else {
                    batchService.save(batch);
                    return JsonResponse.successMessage("添加成功");
                }
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @RequestMapping(value = "/modify", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改批次接口",notes = "应传入：batchId, batchDatestart（时间戳, batchDateend（时间戳")
    public JsonResponse modifyBatch(@RequestBody @Valid Batch batch) {
        User loginUser = SessionUtils.getCurUser();
        System.out.println(loginUser);
        // 只有学校用户才能修改批次
        if (loginUser.getUserType().equals("school")) {
            // 结束时间小于开始时间
            if (batch.getBatchDateend().compareTo(batch.getBatchDatestart()) <= 0 ) {
                return JsonResponse.failure("修改失败，您填写的结束日期早于开始日期");
            } else {
                boolean temp = batchService.updateByBatch(batch);
                if (temp) {
                    return JsonResponse.successMessage("修改成功");
                }
                return JsonResponse.successMessage("修改失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }
}
