package com.team14.sms.controller;


import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ManagerMapper;
import com.team14.sms.service.ManagerService;
import com.team14.sms.dao.Manager;
import com.team14.sms.dao.User;
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
 * @since 2022-06-24
 */
@Controller
@RequestMapping("/api/manager")
public class ManagerController extends BaseController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerMapper managerMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加辅导员接口",notes = "应传入：manId, manName, manPassword")
    public JsonResponse addManager(@RequestBody @Valid Manager manager){
        return managerService.addState(manager);
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "辅导员登录接口",notes = "应传入：id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        Manager manager = new Manager();
        manager.setManId(user.getId());
        manager.setManPassword(user.getPassword());

        return managerService.login(manager);
    }

}
