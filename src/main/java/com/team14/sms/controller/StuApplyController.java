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

    @RequestMapping(value = "/selectStuApply", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "查询当前登录辅导员/学院直属学生的申请记录", notes = "需传入pageNo, pageSize")
    public JsonResponse selectStuApply (@RequestBody @Valid PageDTO pageDTO) {
        // System.out.println(pageDTO.getPageNo());
        return JsonResponse.success(stuApplyService.selectStuApply(pageDTO),"查询成功");
    }
//
//    @RequestMapping(value = "/managerAgree", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    @ApiOperation(value = "辅导员批量同意接口", notes = "需填入id（申请记录的乱码id，非学生id")

}
