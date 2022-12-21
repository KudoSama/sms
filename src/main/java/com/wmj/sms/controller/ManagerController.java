package com.wmj.sms.controller;


import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.ManagerMapper;
import com.wmj.sms.service.ManagerService;
import com.wmj.sms.dao.Manager;
import com.wmj.sms.dao.User;
import com.wmj.sms.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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

}
