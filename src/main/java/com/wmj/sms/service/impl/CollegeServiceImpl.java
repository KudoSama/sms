package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.School;
import com.wmj.sms.utls.SessionUtils;
import com.wmj.sms.dao.College;
import com.wmj.sms.mapper.CollegeMapper;
import com.wmj.sms.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public College getByColId(Long colId) {
        QueryWrapper<College> wrapper =new QueryWrapper<>();
        wrapper.eq("col_id", colId);
        return super.getOne(wrapper);
    }

    @Override
    public JsonResponse getCollegeList() {
        QueryWrapper<College> wrapper =new QueryWrapper<>();
        List<College> list = new ArrayList<>();
        list = collegeMapper.selectList(wrapper);
        for (College college : list) {
            college.setColPassword("");
            college.setUserType("");
        }
        return JsonResponse.success(list);
    }

    @Override
    public JsonResponse login(College college) {
        QueryWrapper<College> wrapper =new QueryWrapper<>();
        wrapper.eq("col_id", college.getColId()).eq("col_password", college.getColPassword());
        College loginCollege = super.getOne(wrapper);

        // System.out.println(loginCollege);
        if (loginCollege != null) {
            User loginUser = new User();

            loginUser.setId(loginCollege.getColId());
            loginUser.setName(loginCollege.getColName());
            loginUser.setUserType(loginCollege.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败");
    }

    @Override
    public JsonResponse addState(College college) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            // 检测装入数据的必要字段是否为空
            if (college.getColId() == null || StringUtils.isBlank(college.getColName())
                    || StringUtils.isBlank(college.getColPassword())) {
                return JsonResponse.failure("添加失败，您未填写学院号、学院名或学院密码!");
            } else {
                try {
                    QueryWrapper<College> wrapper = new QueryWrapper<>();
                    wrapper.eq("col_id", college.getColId());
                    if (super.getOne(wrapper) != null) {
                        return JsonResponse.failure("该学院账号" + college.getColId() + "已存在，请重新检查学院信息");
                    }
                    college.setUserType("2");
                    super.save(college);
                    return JsonResponse.successMessage("添加成功");
                } catch (Exception e) {
                    return JsonResponse.failure("添加失败");
                }
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse resetPassword(College college) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            // 仅学校能进行本项操作
            UpdateWrapper<College> wrapper = new UpdateWrapper<>();
            String password = md5DigestAsHex("123456".getBytes());
            wrapper.eq("col_id", college.getColId())
                    .eq("col_name", college.getColName())
                    .set("col_password", password);
            super.update(null, wrapper);
            return JsonResponse.successMessage("已经重置 " + college.getColName() +" 的账号密码为123456");
        }
        return JsonResponse.failure("重置失败，您无本操作权限，请联系系统管理员!");
    }
}
