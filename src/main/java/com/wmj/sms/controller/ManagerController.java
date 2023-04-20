package com.wmj.sms.controller;


import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Manager;
import com.wmj.sms.dao.User;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.mapper.ManagerMapper;
import com.wmj.sms.service.ManagerService;
import com.wmj.sms.service.StudentService;
import com.wmj.sms.utls.SessionUtils;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
 */
@Controller
@RequestMapping("/api/manager")
public class ManagerController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    final Base64.Decoder decoder = Base64.getDecoder();

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

    @ResponseBody
    @RequestMapping("/getManager")
    @ApiOperation(value = "获取辅导员信息接口")
    public JsonResponse getManager() {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("3")) {
            Manager manager = managerService.getByManId(loginUser.getId());
            manager.setManPassword(null);
            return JsonResponse.success(manager, "获取辅导员信息成功");
        }
        return JsonResponse.failure("获取信息失败，您非辅导员用户");
    }

    @ResponseBody
    @RequestMapping("/modify")
    @ApiOperation(value = "辅导员信息修改接口",notes = "manId, manName, userType,manPassword")
    public JsonResponse modify(@RequestBody @Valid Manager manager) {
        return managerService.modify(manager);
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    @ApiOperation(value = "重置密辅导员码接口")
    public JsonResponse resetPassword(@RequestBody @Valid Manager manager) {
        return managerService.resetPassword(manager);
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "辅导员登录接口",notes = "应传入：id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        Manager manager = new Manager();
        manager.setManId(user.getId());
        manager.setManPassword(new String (decoder.decode(user.getPassword())));

        return managerService.login(manager);
    }

    @RequestMapping(value = "/getManagerList", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取辅导员接口")
    public JsonResponse getManagerList(@RequestBody @Valid PageDTO pageDTO){
        return managerService.getManagerList(pageDTO);

    }

}
