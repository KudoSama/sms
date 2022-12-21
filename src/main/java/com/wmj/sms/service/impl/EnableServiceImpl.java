package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.StudentMapper;
import com.wmj.sms.service.StudentService;
import com.wmj.sms.utls.SessionUtils;
import com.wmj.sms.dao.Enable;
import com.wmj.sms.mapper.EnableMapper;
import com.wmj.sms.service.EnableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.dao.Student;
import com.wmj.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-12-25
 */
@Service
public class EnableServiceImpl extends ServiceImpl<EnableMapper, Enable> implements EnableService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JsonResponse addState(Student student) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("3")) {
            Student selectStudent = studentService.getByStuId(student.getStuId());
            // System.out.println(selectStudent);
            if (selectStudent != null) {
                if (loginUser.getId().equals(selectStudent.getManId())) {
                    QueryWrapper<Enable> wrapper = new QueryWrapper<>();
                    wrapper.eq("stu_id", selectStudent.getStuId());
                    if (super.getOne(wrapper) != null) {
                        return JsonResponse.failure(selectStudent.getStuId() + "学生已经认定为贫困生，请勿重复添加");
                    }
                    super.save(new Enable().setStuId(selectStudent.getStuId()));
                    return JsonResponse.success("添加贫困学生成功");
                } else {
                    return JsonResponse.failure("添加失败，您非本学生的辅导员");
                }
            } else {
                return JsonResponse.failure("添加贫困学生失败，不存在该学生");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限");
    }

    @Override
    public Enable getByStuId (Long stuId) {
        QueryWrapper<Enable> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id", stuId);
        return super.getOne(wrapper);
    }
}
