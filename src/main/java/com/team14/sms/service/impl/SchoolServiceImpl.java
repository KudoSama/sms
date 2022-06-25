package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.vo.School;
import com.team14.sms.mapper.SchoolMapper;
import com.team14.sms.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public School login(School school) {
        QueryWrapper<School> wrapper =new QueryWrapper<>();
        wrapper.eq("sch_id", school.getSchId()).eq("sch_password", school.getSchPassword());
        return super.getOne(wrapper);
    }
}
