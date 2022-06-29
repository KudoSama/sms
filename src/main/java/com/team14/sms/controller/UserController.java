package com.team14.sms.controller;

import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.utls.SecurityUtils;
import com.team14.sms.utls.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @ResponseBody
    @RequestMapping("/getCurUserInfo")
    public JsonResponse getCurUserInfo() {
        // System.out.println(SecurityUtils.getUserInfo());
        try{
            JsonResponse temp = JsonResponse.success(SecurityUtils.getUserInfo());
            return temp;
        } catch(Exception e) {
            return JsonResponse.failure("未登录，请重新登陆");
        }
    }

    @ResponseBody
    @RequestMapping("/refresh")
    public JsonResponse refresh() {
        return SessionUtils.deleteCurUser();
    }
}
