package com.team14.sms.service.impl;

import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.service.StudentService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Enable;
import com.team14.sms.mapper.EnableMapper;
import com.team14.sms.service.EnableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.vo.Student;
import com.team14.sms.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-25
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
            if (loginUser.getId().equals(student.getManId())) {
                Student selectStudent = studentService.getByStuId(student.getStuId());
                if (selectStudent != null) {
                    super.save(new Enable().setStuId(selectStudent.getStuId()));
                    return JsonResponse.success("添加贫困学生成功");
                }
                return JsonResponse.failure("添加贫困学生失败，不存在该学生");
            } else {
                return JsonResponse.failure("添加失败，您非本学生的辅导员");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限");
    }
}
