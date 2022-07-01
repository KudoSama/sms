package com.team14.sms.controller;


import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.SchoolMapper;
import com.team14.sms.dao.User;
import com.team14.sms.service.SchoolService;
import com.team14.sms.dao.School;
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
    @ApiOperation(value = "学校登录接口",notes = "传入id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        School school = new School();
        school.setSchId(user.getId());
        school.setSchPassword(user.getPassword());

        return schoolService.login(school);
    }
}
