package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.service.StuApplyService;
import com.team14.sms.service.StudentService;
import com.team14.sms.vo.StuApply;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.team14.sms.base.BaseController;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.Date;

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

}
