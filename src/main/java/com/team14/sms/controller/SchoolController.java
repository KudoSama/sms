package com.team14.sms.controller;


import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.SchoolMapper;
import com.team14.sms.vo.User;
import com.team14.sms.service.SchoolService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.School;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-06-25
 */
@Controller
@RequestMapping("/api/school")
public class SchoolController extends BaseController {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolMapper schoolMapper;

    @ResponseBody
    @RequestMapping("/login")
    @ApiOperation(value = "学校登录接口")
    public JsonResponse login(@RequestBody School school) {
        School loginSchool = schoolService.login(school);
        if (loginSchool != null) {
            User loginUser = new User();

            loginUser.setId(loginSchool.getSchId());
            loginUser.setName(loginSchool.getSchName());
            loginUser.setUserType(loginSchool.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败");
    }
}
