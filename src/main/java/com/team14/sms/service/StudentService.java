package com.team14.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.vo.Student;
import springfox.documentation.spring.web.json.Json;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
public interface StudentService extends IService<Student> {

    JsonResponse login(Student student);

    Student getByStuId(Long stuId);

    JsonResponse addStudent(Student student);
}
