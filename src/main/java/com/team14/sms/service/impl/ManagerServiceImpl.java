package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ManagerMapper;
import com.team14.sms.service.ManagerService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Manager;
import com.team14.sms.vo.Student;
import com.team14.sms.vo.User;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-24
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Override
    public Manager getByManId(Long manId) {
        QueryWrapper<Manager> wrapper =new QueryWrapper<>();
        wrapper.eq("man_id", manId);
        return super.getOne(wrapper);
    }

    @Override
    public JsonResponse login(Manager manager) {
        QueryWrapper<Manager> wrapper =new QueryWrapper<>();
        wrapper.eq("man_id", manager.getManId()).eq("man_password", manager.getManPassword());
        Manager loginManager = super.getOne(wrapper);
        //System.out.println(loginManager);
        if (loginManager != null) {
            User loginUser = new User();

            loginUser.setId(loginManager.getManId());
            loginUser.setName(loginManager.getManName());
            loginUser.setUserType(loginManager.getUserType());

            SessionUtils.saveCurUser(loginUser);
            return JsonResponse.success(loginUser, "登陆成功");
        }
        return JsonResponse.failure("登陆失败，请检查您的账号和密码");
    }

    @Override
    public JsonResponse addState(Manager manager) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学院用户才能添加辅导员
        if (loginUser.getUserType().equals("2")) {
            // 未填写辅导员号或密码
            if (manager.getManId() == null || StringUtils.isBlank(manager.getManPassword())) {
                return JsonResponse.failure("添加失败，您未填写辅导员号或密码");
            } else {
                try {
                    manager.setColId(loginUser.getId()); // 绑定学院号，无需前端操作
                    manager.setUserType("3");
                    super.save(manager);
                    return JsonResponse.successMessage("添加成功");
                } catch (Exception e) {
                    return JsonResponse.failure(e.toString());
                }
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限");
    }
}
