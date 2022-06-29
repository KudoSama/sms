package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.service.StudentService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Student;
import com.team14.sms.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public JsonResponse login(Student student) {
        QueryWrapper<Student> wrapper =new QueryWrapper<>();
        wrapper.eq("stu_id", student.getStuId()).eq("stu_password", student.getStuPassword());
        Student loginStudent = super.getOne(wrapper);
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

    @Override
    public Student getByStuId(Long stuId) {
        if (stuId == null) {
            return null;
        }
        QueryWrapper<Student> wrapper =new QueryWrapper<>();
        wrapper.eq("stu_id", stuId);
        return super.getOne(wrapper);
    }

    @Override
    public JsonResponse addStudent(Student student) {

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
                    super.save(student);
                    return JsonResponse.successMessage("添加成功");
                } catch (Exception e) {
                    return JsonResponse.failure("添加失败");
                }
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse getByGender(String gender) {
        if (gender.equals("男") || gender.equals("女")) {
            QueryWrapper<Student> wrapper =new QueryWrapper<>();
            wrapper.eq("gender", gender);
            List<Student> stuList = super.list(wrapper);
            return JsonResponse.success(stuList, "查询成功");
        }
        return JsonResponse.failure("查询失败，未填写性别或性别填写失败，应填写男或女");
    }

    @Override
    public List<Student> getByManId() {
        User loginUser = SessionUtils.getCurUser();
        Long manId = null;
        // 通过辅导员号查询属于该辅导员的学生
        if (loginUser.getUserType().equals("3")) {
            manId = loginUser.getId();
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("man_id", manId);
            return super.list(wrapper);
        }
        return null;
    }
}
