package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ManagerMapper;
import com.team14.sms.service.ManagerService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Manager;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.team14.sms.base.BaseController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/manager")
public class ManagerController extends BaseController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerMapper managerMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加辅导员接口",notes = "应传入：manId, manName, manPassword, coId")
    public JsonResponse addManager(@RequestBody @Valid Manager manager){
        try {
            managerService.save(manager);
            return JsonResponse.successMessage("添加成功");
        } catch (Exception e) {
            return JsonResponse.failure(e.toString());
        }
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "辅导员登录接口")
    public JsonResponse login(@RequestBody @Valid Manager manager) {
        Manager loginManager = managerService.login(manager);
        //System.out.println(loginManager);
        if (loginManager != null) {
            User loginUser = new User();

            loginUser.setId(loginManager.getManId());
            loginUser.setName(loginManager.getManName());
            loginUser.setUserType(loginManager.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败");
    }

}
