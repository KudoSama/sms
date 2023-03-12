package com.wmj.sms.controller;

import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.utls.SecurityUtils;
import com.wmj.sms.utls.SessionUtils;
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
            JsonResponse jsonResponse = new JsonResponse<>();
            jsonResponse.setStatus(false);
            jsonResponse.setCode(70005); // 前端使用70005来检测未登录
            jsonResponse.setMessage("未登录，请重新登陆");
            return jsonResponse;
        }
    }

    @ResponseBody
    @RequestMapping("/refresh")
    public JsonResponse refresh() {
        return SessionUtils.deleteCurUser();
    }
}
