package com.wmj.sms.controller;


import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.StuApply;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.service.StuApplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
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

    @RequestMapping(value = "/studentModify", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学生在申请时间内修改申请记录", notes = "需填入id, batchId、clothId、clothSize、appReason")
    public JsonResponse studentModify(@RequestBody @Valid StuApply stuApply) {
        return stuApplyService.studentModify(stuApply);
    }

    @RequestMapping(value = "/studentSelect", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学生查询自己的申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse studentSelect(@RequestBody @Valid PageDTO pageDTO) {
        if (stuApplyService.studentSelect(pageDTO) == null) {
            return JsonResponse.failure("当前不属于申请时间");
        }
        return JsonResponse.success(stuApplyService.studentSelect(pageDTO), "查询成功");
    }

    @RequestMapping(value = "/schoolModify", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学校修改学生衣服和尺码接口", notes = "应传入id、batchId、clothId、clothSize")
    public JsonResponse schoolModify(@RequestBody @Valid StuApply stuApply) {
        return stuApplyService.schoolModify(stuApply);
    }

    @RequestMapping(value = "/selectNotExamineStuApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前登录辅导员/学院直属学生中未审核的的申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse selectNotExamineStuApply(@RequestBody @Valid PageDTO pageDTO) {
        if (stuApplyService.selectExaminedStuApply(pageDTO) == null) {
            return JsonResponse.failure("当前不属于审批时间，请等待学生申请结束");
        }
        return JsonResponse.success(stuApplyService.selectNotExamineStuApply(pageDTO), "查询成功");
    }

    @RequestMapping(value = "/selectAllStuApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前所有申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse selectAllStuApply(@RequestBody @Valid PageDTO pageDTO) {
        if (stuApplyService.selectAllStuApply(pageDTO) == null) {
            return JsonResponse.failure("当前不属于审批时间，请等待学生申请结束");
        }
        return JsonResponse.success(stuApplyService.selectAllStuApply(pageDTO), "查询成功");
    }

    @RequestMapping(value = "/selectExaminedStuApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前登录辅导员/学院直属学生中已审核的的申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse selectExaminedStuApply(@RequestBody @Valid PageDTO pageDTO) {
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

    @RequestMapping(value = "/deleteApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除申请记录接口", notes = "擦混入记录ID")
    public JsonResponse deleteApply(@RequestBody @Valid StuApply stuApply) {
        return stuApplyService.deleteApply(stuApply);
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

    @RequestMapping(value = "/exportState", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "导出当前待审批结束的批次的审核状态接口", notes = " ")
    public void exportState(HttpServletResponse response) {
        stuApplyService.exportState(response);
    }

    @RequestMapping(value = "/exportResult", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "导出统计每个学院每个款式男女每个尺码各有多少个学生选了的接口", notes = " ")
    public void exportResult(HttpServletResponse response) {
        stuApplyService.exportResult(response);
    }

    @RequestMapping(value = "/getStateByStu", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取学生当前批次的所有申请的审核状态", notes = "随便用")
    public JsonResponse getStateByStu() {
        return stuApplyService.getStateByStu(); // 返回的是带申请list的JsonResponse
    }
}
