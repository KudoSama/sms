package com.wmj.sms.controller;


import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.CollegeMapper;
import com.wmj.sms.service.CollegeService;
import com.wmj.sms.dao.College;
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
import com.wmj.sms.base.BaseController;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
 */
@Controller
@RequestMapping("/api/college")
public class CollegeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    final Base64.Decoder decoder = Base64.getDecoder();

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

    @RequestMapping(value = "/getCollegeList", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加学院接口")
    public JsonResponse getCollegeList(){
        return collegeService.getCollegeList();
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    @ApiOperation(value = "重置密学院码接口")
    public JsonResponse resetPassword(@RequestBody @Valid College college) {
        return collegeService.resetPassword(college);
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学院登录接口", notes = "应传入id password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        College college = new College();
        college.setColId(user.getId());
        college.setColPassword(new String (decoder.decode(user.getPassword())));

        return collegeService.login(college);
    }

    @ResponseBody
    @RequestMapping("/modify")
    @ApiOperation(value = "学院信息修改接口",notes = "colId, colName, userType,colPassword")
    public JsonResponse modify(@RequestBody @Valid College college) {
        return collegeService.modify(college);
    }

    @ResponseBody
    @RequestMapping("/getCollege")
    @ApiOperation(value = "获取学院信息接口")
    public JsonResponse getCollege() {
        return collegeService.getCollege();
    }

}
