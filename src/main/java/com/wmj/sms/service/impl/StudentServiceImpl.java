package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.StuApply;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.mapper.StudentMapper;
import com.wmj.sms.service.StudentService;
import com.wmj.sms.utls.SessionUtils;
import com.wmj.sms.dao.Student;
import com.wmj.sms.dao.User;
import org.springframework.stereotype.Service;

import java.security.cert.PKIXParameters;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
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
    public JsonResponse addState(Student student) {

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
                    QueryWrapper<Student> wrapper = new QueryWrapper<>();
                    wrapper.eq("stu_id", student.getStuId());
                    if (super.getOne(wrapper) != null) {
                        return JsonResponse.failure("该学生账号 " + student.getStuId() +" 已存在，请重新检查学生信息");
                    }
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
    public JsonResponse getByGender(String gender, PageDTO pageDTO) {
        if (gender.equals("男") || gender.equals("女")) {
            User loginUser = SessionUtils.getCurUser();
            if (loginUser.getUserType().equals("3")) {
                Long manId = loginUser.getId();
                Page<Student> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
                QueryWrapper<Student> wrapper = new QueryWrapper<>();
                wrapper.eq("gender", gender).eq("man_id", manId);
                page = super.page(page, wrapper);
                List<Student> stuList = page.getRecords();
                for (Student student: stuList) {
                    student.setStuPassword("");
                }
                page.setRecords(stuList);
                return JsonResponse.success(page, "查询成功");
            } else if (loginUser.getUserType().equals("2")) {
                Long colId = loginUser.getId();
                Page<Student> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
                QueryWrapper<Student> wrapper = new QueryWrapper<>();
                wrapper.eq("gender", gender).eq("col_id", colId);
                page = super.page(page, wrapper);
                List<Student> stuList = page.getRecords();
                for (Student student: stuList) {
                    student.setStuPassword("");
                }
                page.setRecords(stuList);
                return JsonResponse.success(page, "查询成功");
            }
        }
        return JsonResponse.failure("查询失败，未填写性别或性别填写失败，应填写男或女");
    }

    @Override
    public JsonResponse getByManagerId(PageDTO pageDTO) {
        User loginUser = SessionUtils.getCurUser();
        // 通过辅导员号查询属于该辅导员的学生
        if (loginUser.getUserType().equals("3")) {
            Long manId = null;
            manId = loginUser.getId();
            Page<Student> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("man_id", manId);
            page = super.page(page, wrapper);
            List<Student> stuList = page.getRecords();
            for (Student student: stuList) {
                student.setStuPassword("");
            }
            page.setRecords(stuList);
            return JsonResponse.success(page, "查询成功");
        } else if (loginUser.getUserType().equals("2")) { // 通过学院号查询属于该学院的学生
            Long colId = null;
            colId = loginUser.getId();
            Page<Student> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("col_id", colId);
            page = super.page(page, wrapper);
            List<Student> stuList = page.getRecords();
            for (Student student: stuList) {
                student.setStuPassword("");
            }
            page.setRecords(stuList);
            return JsonResponse.success(page, "查询成功");
        }
        return JsonResponse.failure("您非辅导员，无权限查询学生");
    }
}
