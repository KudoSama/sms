package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.service.StuApplyService;
import com.team14.sms.vo.StuApply;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import com.team14.sms.base.BaseController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-06-27
 */
@Controller
@RequestMapping("/api/stuApply")
public class StuApplyController extends BaseController {

    @Autowired
    private StuApplyService stuApplyService;

    @RequestMapping(value = "/apply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学生申请补助", notes = "需填入batchId、clothId、clothSize、appReason")
    public JsonResponse apply(@RequestBody @Valid StuApply stuApply) {
        return stuApplyService.apply(stuApply);
    }

    @RequestMapping(value = "/selectNotExamineStuApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前登录辅导员/学院直属学生中未审核的的申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse selectNotExamineStuApply(@RequestBody @Valid PageDTO pageDTO) {
        // System.out.println(pageDTO.getPageNo());
        if (stuApplyService.selectExaminedStuApply(pageDTO) == null) {
            return JsonResponse.failure("当前不属于审批时间，请等待学生申请结束");
        }
        return JsonResponse.success(stuApplyService.selectNotExamineStuApply(pageDTO), "查询成功");
    }

    @RequestMapping(value = "/selectExaminedStuApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前登录辅导员/学院直属学生中已审核的的申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse selectExaminedStuApply(@RequestBody @Valid PageDTO pageDTO) {
        // System.out.println(pageDTO.getPageNo());
        if (stuApplyService.selectExaminedStuApply(pageDTO) == null) {
            return JsonResponse.failure("当前不属于审批时间，请等待学生申请结束");
        }
        return JsonResponse.success(stuApplyService.selectExaminedStuApply(pageDTO), "查询成功");
    }

    @RequestMapping(value = "/agreeBatch", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "高级用户（非学生）批量同意接口", notes = "需填入idList（申请记录的乱码id，非学生id,JSON数组格式为[number1, number2]")
    public JsonResponse agreeBatch(@RequestBody @Valid List<Long> idList) {
        if (!stuApplyService.agreeBatch(idList)) {
            return JsonResponse.failure("同意审批失败");
        }
        return JsonResponse.success("同意审批成功");
    }

    @RequestMapping(value = "/disagreeBatch", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "高级用户（非学生）批量拒绝接口", notes = "应传入id和refReason，需要批量处理就传入[id, refReason],[id, refReason]")
    public JsonResponse disagreeBatch(@RequestBody @Valid List<StuApply> list) {
        if (!stuApplyService.disagreeBatch(list)) {
            return JsonResponse.failure("拒绝审批失败");
        }
        return JsonResponse.success("拒绝审批成功");
    }
}
