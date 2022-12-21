package com.wmj.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Student;
import com.wmj.sms.dto.PageDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
 */
public interface StudentService extends IService<Student> {

    JsonResponse login(Student student);

    Student getByStuId(Long stuId);

    JsonResponse addState(Student student);

    JsonResponse getByGender(String gender, PageDTO pageDTO);

    JsonResponse getByManagerId(PageDTO pageDTO);
}
