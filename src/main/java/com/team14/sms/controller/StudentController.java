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
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.List;

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
        return studentService.addStudent(student);
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学生登录接口",notes = "应传入：id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        Student student = new Student();
        student.setStuId(user.getId());
        student.setStuPassword(user.getPassword());

        return studentService.login(student);
    }

    @RequestMapping(value = "/getByStuId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "根据学号查询学生", notes = "应传入id")
    public JsonResponse getByStuId(@RequestBody @Valid User user) {
        Student student = studentService.getByStuId(user.getId());
        if (student == null) {
            return JsonResponse.failure("查询失败，不存在该学生");
        }
        return JsonResponse.success(student, "查询成功");
    }

    @RequestMapping(value = "getByGender", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "通过性别查询学生列表", notes = "应传入：gender(string类型")
    @ApiImplicitParam(name = "gender", value = "性别", required = true, dataType = "String",dataTypeClass = String.class)
    public JsonResponse getByGender(String gender) {
        return studentService.getByGender(gender);
    }
}
