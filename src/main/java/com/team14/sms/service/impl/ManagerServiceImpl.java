package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.mapper.ManagerMapper;
import com.team14.sms.service.ManagerService;
import com.team14.sms.vo.Manager;
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
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Override
    public Manager getByManId(Long manId) {
        QueryWrapper<Manager> wrapper =new QueryWrapper<>();
        wrapper.eq("man_id", manId);
        return super.getOne(wrapper);
    }

    @Override
    public Manager login(Manager manager) {
        QueryWrapper<Manager> wrapper =new QueryWrapper<>();
        wrapper.eq("man_id", manager.getManId()).eq("man_password", manager.getManPassword());
        return super.getOne(wrapper);
    }
}
