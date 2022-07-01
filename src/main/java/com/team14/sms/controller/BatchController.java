package com.team14.sms.controller;


import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.BatchMapper;
import com.team14.sms.service.BatchService;
import com.team14.sms.dao.Batch;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return batchService.addState(batch);
    }

    @RequestMapping(value = "/modify", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "修改批次接口",notes = "应传入：应传入：batchId, batchDatestart（时间戳, batchDateend（时间戳")
    public JsonResponse modifyBatch(@RequestBody @Valid Batch batch) {
        return batchService.modifyBatch(batch);
    }

    @RequestMapping(value = "/getCurBatch", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前批次")
    public JsonResponse getCurBatch() {
        return batchService.getCurBatch();
    }

    @RequestMapping(value = "/getNotExamineBatch", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询待审批批次", notes = "检索已经结束批次中，结束时间 + 1 month比当前时间大的批次")
    public JsonResponse getNotExamineBatch() {
        return batchService.getNotExamineBatch();
    }
}
