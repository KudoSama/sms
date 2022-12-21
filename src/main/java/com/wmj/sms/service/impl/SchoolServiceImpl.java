package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.MailRequest;
import com.wmj.sms.service.SendMailService;
import com.wmj.sms.utls.SessionUtils;
import com.wmj.sms.dao.School;
import com.wmj.sms.mapper.SchoolMapper;
import com.wmj.sms.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-12-25
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    @Autowired
    private SendMailService sendMailService;

    final Base64.Decoder decoder = Base64.getDecoder();

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

    @Override
    public JsonResponse getSchool() {
        User loginUser = SessionUtils.getCurUser();
        // 仅学校可以修改学生申请
        if (loginUser.getUserType().equals("1")) {
            QueryWrapper<School> wrapper = new QueryWrapper<>();
            wrapper.eq("sch_id", loginUser.getId());
            School school = super.getOne(wrapper);
            school.setSchPassword(null);
            return JsonResponse.success(school, "获取学校信息成功");
        }
        return JsonResponse.failure("获取信息失败，您非学校用户录");
    }

    @Override
    public JsonResponse modify(School school, int mail, int num) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学校可以修改账户信息
        if (loginUser.getUserType().equals("1")) {
            if (mail != num) {
                return JsonResponse.failure("邮件验证码错误，请检查填写的邮箱或验证码后重新填写");
            }
            // 信息未填写完毕
            if (school.getSchName() == null ||
            school.getSchEmail() == null) {
                return JsonResponse.failure("请检查您是否填写了用户名、密码和电子邮箱");
            } else if (school.getSchPassword() == null) {
                UpdateWrapper<School> wrapper = new UpdateWrapper<>();
                wrapper.eq("sch_id", school.getSchId()).set("sch_name", school.getSchName())
                        .set("sch_email", school.getSchEmail());
                super.update(null, wrapper);
                return JsonResponse.successMessage("信息已完成修改，请注意保管");
            }
            else {
                UpdateWrapper<School> wrapper = new UpdateWrapper<>();
                wrapper.eq("sch_id", school.getSchId()).set("sch_name", school.getSchName()).
                        set("sch_password",new String(decoder.decode(school.getSchPassword()))).set("sch_email", school.getSchEmail());
                super.update(null, wrapper);
                return JsonResponse.successMessage("信息已完成修改，请注意保管");
            }
        }
        return JsonResponse.failure("修改信息失败，您非学校用户，无权限修改学生申请记录");
    }

    @Override
    public JsonResponse setEmail(int num) {
        MailRequest mailRequest = new MailRequest();
        QueryWrapper<School> wrapper = new QueryWrapper<>();
        School school = super.getOne(wrapper);
        mailRequest.setSendTo(school.getSchEmail());
        mailRequest.setSubject("修改密码专用验证邮件");
        mailRequest.setText("验证码为：" + num + "\n此邮件为系统自动发送，请勿回复。");
        sendMailService.sendSimpleMail(mailRequest);
        return JsonResponse.successMessage("发送成功，请注意查收");
    }

    public JsonResponse setEmailById(String email, int num) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSendTo(email);
        mailRequest.setSubject("修改密码专用验证邮件");
        mailRequest.setText("验证码为：" + num + "\n此邮件为系统自动发送，请勿回复。");
        sendMailService.sendSimpleMail(mailRequest);
        return JsonResponse.successMessage("发送成功，请注意查收");
    }

    @Override
    public JsonResponse resetPassword(int inNum, int num) {
        if (inNum != num) {
            return JsonResponse.failure("邮件验证码错误，请检查填写的验证码后重新填写");
        }
        UpdateWrapper<School> wrapper = new UpdateWrapper<>();
        String password = md5DigestAsHex("123456".getBytes());
        wrapper.set("sch_password", password);
        super.update(null, wrapper);
        return JsonResponse.successMessage("已经重置密码为123456，请及时登录后修改密码");
    }
}
