package com.wmj.sms.controller;


import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.EnableMapper;
import com.wmj.sms.service.EnableService;
import com.wmj.sms.dao.Student;
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
 * @since 2022-12-25
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
