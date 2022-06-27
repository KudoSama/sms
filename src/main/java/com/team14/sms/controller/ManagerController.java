package com.team14.sms.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ManagerMapper;
import com.team14.sms.service.ManagerService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Manager;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

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
        User loginUser = SessionUtils.getCurUser();
        // 仅学院用户才能添加辅导员
        if (loginUser.getUserType().equals("2")) {
            // 未填写辅导员号或密码
            if (manager.getManId() == null || StringUtils.isBlank(manager.getManPassword())) {
                return JsonResponse.failure("添加失败，您未填写辅导员号或密码");
            } else {
                try {
                    manager.setColId(loginUser.getId()); // 绑定学院号，无需前端操作
                    manager.setUserType("3");
                    managerService.save(manager);
                    return JsonResponse.successMessage("添加成功");
                } catch (Exception e) {
                    return JsonResponse.failure(e.toString());
                }
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限");
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "辅导员登录接口",notes = "应传入：id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        Manager manager = new Manager();
        manager.setManId(user.getId());
        manager.setManPassword(user.getPassword());
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
        return JsonResponse.failure("登陆失败，请检查您的账号和密码");
    }

}
