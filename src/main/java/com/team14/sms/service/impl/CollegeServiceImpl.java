package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.vo.College;
import com.team14.sms.mapper.CollegeMapper;
import com.team14.sms.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.vo.Manager;
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
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Override
    public College getByColId(Long colId) {
        QueryWrapper<College> wrapper =new QueryWrapper<>();
        wrapper.eq("col_id", colId);
        return super.getOne(wrapper);
    }

    @Override
    public College login(College college) {
        QueryWrapper<College> wrapper =new QueryWrapper<>();
        wrapper.eq("col_id", college.getColId()).eq("col_password", college.getColPassword());
        return super.getOne(wrapper);
    }
}
