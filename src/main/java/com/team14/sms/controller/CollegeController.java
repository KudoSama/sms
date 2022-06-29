package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.CollegeMapper;
import com.team14.sms.service.CollegeService;
import com.team14.sms.vo.College;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
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
@Controller
@RequestMapping("/api/college")
public class CollegeController extends BaseController {


    @Autowired
    private CollegeService collegeService;

    @Autowired
    private CollegeMapper collegeMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加学院接口",notes = "应传入：colId,colName,colPassword")
    public JsonResponse addCollege(@RequestBody @Valid College college){
        return collegeService.addState(college);

    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学院登录接口", notes = "应传入id password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        College college = new College();
        college.setColId(user.getId());
        college.setColPassword(user.getPassword());

        return collegeService.login(college);
    }

}
