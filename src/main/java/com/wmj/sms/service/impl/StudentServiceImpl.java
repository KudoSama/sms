package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Manager;
import com.wmj.sms.dao.StuApply;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.mapper.StudentMapper;
import com.wmj.sms.service.EnableService;
import com.wmj.sms.service.StuApplyService;
import com.wmj.sms.service.StudentService;
import com.wmj.sms.utls.SessionUtils;
import com.wmj.sms.dao.Student;
import com.wmj.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

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

    @Autowired
    private EnableService enableService;

    @Autowired
    private StuApplyService stuApplyService;

    final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public JsonResponse login(Student student) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
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
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id", stuId);
        Student temp = super.getOne(wrapper);
        temp.setStuPassword(null);
        return temp;
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
                        return JsonResponse.failure("该学生账号 " + student.getStuId() + " 已存在，请重新检查学生信息");
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
    public JsonResponse deleteState(Student student) {
        User loginUser = SessionUtils.getCurUser();
        // 仅辅导员用户才能删除学生
        if (loginUser.getUserType().equals("3")) {
            enableService.delete(student);
            StuApply stuApply = new StuApply();
            stuApply.setStuId(student.getStuId());
            stuApplyService.deleteApplyByStuId(stuApply);
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("stu_id", student.getStuId());
            super.remove(wrapper);
            return JsonResponse.successMessage("删除成功");
        }
        return JsonResponse.failure("删除失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse modify(Student student) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学生可以修改账户信息
        if (loginUser.getUserType().equals("4")) {
            if (student.getStuName() == null) {
                return JsonResponse.failure("请检查您是否填写了用户名");
            } else if (student.getStuPassword() == null) {
                UpdateWrapper<Student> wrapper = new UpdateWrapper<>();
                wrapper.eq("stu_id", student.getStuId()).set("stu_name", student.getStuName());
                super.update(null, wrapper);
                return JsonResponse.successMessage("信息已完成修改，请注意保管");
            } else {
                UpdateWrapper<Student> wrapper = new UpdateWrapper<>();
                wrapper.eq("stu_id", student.getStuId()).set("stu_name", student.getStuName()).
                        set("stu_password", new String(decoder.decode(student.getStuPassword())));
                super.update(null, wrapper);
                return JsonResponse.successMessage("信息已完成修改，请注意保管");
            }
        }
        return JsonResponse.failure("修改信息失败，您非学生用户，无权限修改账户信息");
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
                for (Student student : stuList) {
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
                for (Student student : stuList) {
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
            Long manId = loginUser.getId();
            Page<Student> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("man_id", manId);
            page = super.page(page, wrapper);
            List<Student> stuList = page.getRecords();
            for (Student student : stuList) {
                student.setStuPassword("");
            }
            page.setRecords(stuList);
            return JsonResponse.success(page, "查询成功");
        } else if (loginUser.getUserType().equals("2")) { // 通过学院号查询属于该学院的学生
            Long colId = loginUser.getId();
            Page<Student> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("col_id", colId);
            page = super.page(page, wrapper);
            List<Student> stuList = page.getRecords();
            for (Student student : stuList) {
                student.setStuPassword("");
            }
            page.setRecords(stuList);
            return JsonResponse.success(page, "查询成功");
        }
        return JsonResponse.failure("您非辅导员或学院，无权限查询学生");
    }

    @Override
    public JsonResponse resetPassword(Student student) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("3")) {
            // 仅辅导员能进行本项操作
            UpdateWrapper<Student> wrapper = new UpdateWrapper<>();
            String password = md5DigestAsHex("123456".getBytes());
            wrapper.eq("stu_id", student.getStuId())
                    .eq("stu_name", student.getStuName())
                    .set("stu_password", password);
            super.update(null, wrapper);
            return JsonResponse.successMessage("已经重置 " + student.getStuName() + " 的账号密码为123456");
        }
        return JsonResponse.failure("重置失败，您无本操作权限，请联系系统管理员!");
    }
}
