package com.wmj.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wmj.sms.base.BaseController;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.College;
import com.wmj.sms.dao.Manager;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.mapper.StudentMapper;
import com.wmj.sms.service.StudentService;
import com.wmj.sms.dao.Student;
import com.wmj.sms.dao.User;
import com.wmj.sms.utls.SessionUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
@RequestMapping("/api/student")
public class StudentController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    final Base64.Decoder decoder = Base64.getDecoder();

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加学生接口",notes = "应传入：stuId, stuName, gender, enDate（时间戳, classId, stuPassword")
    public JsonResponse addStudent(@RequestBody @Valid Student student){
        return studentService.addState(student);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除学生接口",notes = "应传入：stuId")
    public JsonResponse deleteStudent(@RequestBody @Valid Student student){
        return studentService.deleteState(student);
    }

    @ResponseBody
    @RequestMapping("/getStudent")
    @ApiOperation(value = "获取学生信息接口")
    public JsonResponse getStudent() {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("4")) {
            Student student = studentService.getByStuId(loginUser.getId());
            student.setStuPassword(null);
            return JsonResponse.success(student, "获取学生信息成功");
        }
        return JsonResponse.failure("获取信息失败，您非学生用户");
    }

    @ResponseBody
    @RequestMapping("/modify")
    @ApiOperation(value = "学生信息修改接口",notes = "stuId, stuName, userType,stuPassword")
    public JsonResponse modify(@RequestBody @Valid Student student) {
        return studentService.modify(student);
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "学生登录接口",notes = "应传入：id,password")
    public JsonResponse login(@RequestBody @Valid User user) {
        // 转打包
        Student student = new Student();
        student.setStuId(user.getId());
        student.setStuPassword(new String (decoder.decode(user.getPassword())));

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

    @RequestMapping(value = "/getByGender", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "通过性别查询学生列表", notes = "应传入：gender(string类型")
    public JsonResponse getByGender(@RequestBody @Valid Map<String, Object> map) {
        PageDTO pageDTO = objectMapper.convertValue(map.get("pageList"), PageDTO.class);
        String gender = objectMapper.convertValue(map.get("gender"), String.class);
        return studentService.getByGender(gender, pageDTO);
    }

    @RequestMapping(value = "/getByManagerId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "通过辅导员/学院账号查询学生列表")
    public JsonResponse getByManagerId(@RequestBody @Valid PageDTO pageDTO) {
        return studentService.getByManagerId(pageDTO);
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    @ApiOperation(value = "重置密学生码接口")
    public JsonResponse resetPassword(@RequestBody @Valid Student student) {
        return studentService.resetPassword(student);
    }
}
