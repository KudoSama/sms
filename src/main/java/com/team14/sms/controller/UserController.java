package com.team14.sms.controller;

import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.utls.SecurityUtils;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @ResponseBody
    @RequestMapping("/getCurUserInfo")
    public JsonResponse getCurUserInfo() {
        // System.out.println(SecurityUtils.getUserInfo());
        return JsonResponse.success(SecurityUtils.getUserInfo());
    }

    @ResponseBody
    @RequestMapping("/refresh")
    public JsonResponse refresh() {
        return SessionUtils.deleteCurUser();
    }
}
