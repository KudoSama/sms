package com.team14.sms.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.CollegeMapper;
import com.team14.sms.service.CollegeService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.College;
import com.team14.sms.vo.Manager;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            // 检测装入数据的必要字段是否为空
            if (college.getColId() == null || StringUtils.isBlank(college.getColName())
                    || StringUtils.isBlank(college.getColPassword())) {
                return JsonResponse.failure("添加失败，您未填写学院号、学院名或学院密码!");
            } else {
                try {
                    college.setUserType("2");
                    collegeService.save(college);
                    return JsonResponse.successMessage("添加成功");
                } catch (Exception e) {
                    return JsonResponse.failure("添加失败");
                }
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");

    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学院登录接口", notes = "应传入id password")
    public JsonResponse login(@RequestBody @Valid User user) {
        College college = new College();
        college.setColId(user.getId());
        college.setColPassword(user.getPassword());
        College loginCollege = collegeService.login(college);
        // System.out.println(loginCollege);
        if (loginCollege != null) {
            User loginUser = new User();

            loginUser.setId(loginCollege.getColId());
            loginUser.setName(loginCollege.getColName());
            loginUser.setUserType(loginCollege.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败");
    }

}
