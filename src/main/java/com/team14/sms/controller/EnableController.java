package com.team14.sms.controller;


import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.EnableMapper;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.service.EnableService;
import com.team14.sms.service.StudentService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Enable;
import com.team14.sms.vo.Student;
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
 * @since 2022-06-25
 */
@Controller
@RequestMapping("/api/enable")
public class EnableController extends BaseController {

    @Autowired
    private EnableService enableService;

    @Autowired
    private EnableMapper enableMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加贫困名单接口",notes = "传入stuId")
    public JsonResponse add(@RequestBody @Valid Student student) {
        return enableService.addState(student);
    }
}
