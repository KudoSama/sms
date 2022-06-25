package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.vo.User;
import com.team14.sms.service.StudentService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Student;
import io.swagger.annotations.ApiOperation;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {



    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加学生接口",notes = "应传入：stuId, stuName, gender, enDate, classId, manId, stuPassword")
    public JsonResponse addStudent(@RequestBody @Valid Student student){
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("manager")) {
            try {
                studentService.save(student);
                return JsonResponse.successMessage("添加成功");
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学生登录接口")
    public JsonResponse login(@RequestBody @Valid Student student) {
        Student loginStudent = studentService.login(student);
        if (loginStudent != null) {
            User loginUser = new User();

            loginUser.setId(loginStudent.getStuId());
            loginUser.setName(loginStudent.getStuName());
            loginUser.setUserType(loginStudent.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败");
    }
}
