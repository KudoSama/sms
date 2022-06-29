package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.School;
import com.team14.sms.mapper.SchoolMapper;
import com.team14.sms.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.vo.User;
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
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    @Override
    public JsonResponse login(School school) {
        QueryWrapper<School> wrapper =new QueryWrapper<>();
        wrapper.eq("sch_id", school.getSchId()).eq("sch_password", school.getSchPassword());
        School loginSchool = super.getOne(wrapper);
        if (loginSchool != null) {
            User loginUser = new User();

            loginUser.setId(loginSchool.getSchId());
            loginUser.setName(loginSchool.getSchName());
            loginUser.setUserType(loginSchool.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败，请检查您的账号和密码");
    }
}
