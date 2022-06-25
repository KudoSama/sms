package com.team14.sms.controller;

import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.utls.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @ResponseBody
    @RequestMapping("/getCurUserInfo")
    public JsonResponse getCurUserInfo() {
        // System.out.println(SecurityUtils.getUserInfo());
        return JsonResponse.success(SecurityUtils.getUserInfo());
    }
}
