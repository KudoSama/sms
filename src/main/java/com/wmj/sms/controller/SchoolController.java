package com.wmj.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.SchoolMapper;
import com.wmj.sms.dao.User;
import com.wmj.sms.service.SchoolService;
import com.wmj.sms.dao.School;
import com.wmj.sms.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-12-25
 */
@Controller
@RequestMapping("/api/school")
public class SchoolController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    final Base64.Decoder decoder = Base64.getDecoder();

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private int num;

    @ResponseBody
    @RequestMapping("/login")
    @ApiOperation(value = "学校登录接口",notes = "传入id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        School school = new School();
        school.setSchId(user.getId());
        school.setSchPassword(new String (decoder.decode(user.getPassword())));

        return schoolService.login(school);
    }

    @ResponseBody
    @RequestMapping("/getSchool")
    @ApiOperation(value = "获取学校信息接口")
    public JsonResponse getSchool() {
        return schoolService.getSchool();
    }

    @ResponseBody
    @RequestMapping("/getSchoolById")
    @ApiOperation(value = "获取学校信息接口")
    public JsonResponse getSchoolById( @RequestBody @Valid School school) {
        return schoolService.getSchoolById(school);
    }

    @ResponseBody
    @RequestMapping("/modify")
    @ApiOperation(value = "学校信息修改接口",notes = "schId, schName, userType,schPassword, schEmail")
    public JsonResponse modify(@RequestBody @Valid Map<String,Object> map) {
        // 转打包
        School school = objectMapper.convertValue(map.get("school"), School.class);
        int email = objectMapper.convertValue(map.get("email"), int.class); // 输入的验证码
        int data = num;
        num = 11451411;
        return schoolService.modify(school, email, data);
    }

    @ResponseBody
    @RequestMapping("/setEmail")
    @ApiOperation(value = "发送电子邮件接口")
    public JsonResponse setEmail() {
        num = (int) (Math.random()*900000) + 100000;
        return schoolService.setEmail(num);
    }

    @ResponseBody
    @RequestMapping("/setEmailById")
    @ApiOperation(value = "发送电子邮件接口")
    public JsonResponse setEmailById(@RequestBody @Valid Map<String,Object> map) {
        String email = (String) map.get("email");
        num = (int) (Math.random()*900000) + 100000;
        return schoolService.setEmailById(email, num);
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    @ApiOperation(value = "学校重置密码接口",notes = "inNum")
    public JsonResponse resetPassword(@RequestBody @Valid Map<String, Integer> map) {
        int data = num;
        num = 11451411;
        return schoolService.resetPassword(map.get("inNum"), data);
    }

}
