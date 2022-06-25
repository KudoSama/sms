package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.mapper.StudentMapper;
import com.team14.sms.service.StudentService;
import com.team14.sms.vo.Student;
import org.springframework.stereotype.Service;

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
    public Student login(Student student) {
        QueryWrapper<Student> wrapper =new QueryWrapper<>();
        wrapper.eq("stu_id", student.getStuId()).eq("stu_password", student.getStuPassword());
        return super.getOne(wrapper);
    }

    @Override
    public Student getByStuId(Long stuId) {
        QueryWrapper<Student> wrapper =new QueryWrapper<>();
        wrapper.eq("stu_id", stuId);
        return super.getOne(wrapper);
    }
}
