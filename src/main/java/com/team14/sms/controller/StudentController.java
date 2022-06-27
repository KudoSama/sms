package com.team14.sms.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team14.sms.base.BaseController;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.service.StudentService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Student;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/student")
public class StudentController extends BaseController {



    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加学生接口",notes = "应传入：stuId, stuName, gender, enDate（时间戳, classId, stuPassword")
    public JsonResponse addStudent(@RequestBody @Valid Student student){
        User loginUser = SessionUtils.getCurUser();
        // 仅辅导员用户才能添加学生
        if (loginUser.getUserType().equals("3")) {
            // 未填写学生号、性别、入学时间、班级号或密码
            if (student.getStuId() == null || StringUtils.isBlank(student.getGender()) || student.getEnDate() == null ||
            student.getClassId() == null || StringUtils.isBlank(student.getStuPassword())) {
                return JsonResponse.failure("添加失败，请检查学号、性别、入学时间、班级号、密码是否已经填写");
            } else {
                try {
                    student.setManId(loginUser.getId()); // 绑定当前辅导员号，无需前端操作
                    student.setUserType("4");
                    studentService.save(student);
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
    @ApiOperation(value = "学生登录接口",notes = "应传入：id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        Student student = new Student();
        student.setStuId(user.getId());
        student.setStuPassword(user.getPassword());
        System.out.println(student.getStuId());
        Student loginStudent = studentService.login(student);
        if (loginStudent != null) {
            User loginUser = new User();

            loginUser.setId(loginStudent.getStuId());
            loginUser.setName(loginStudent.getStuName());
            loginUser.setUserType(loginStudent.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败，请检查您的账号和密码");
    }
}
