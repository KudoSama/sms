package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.vo.Cloth;
import com.team14.sms.mapper.ClothMapper;
import com.team14.sms.service.ClothService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@Service
public class ClothServiceImpl extends ServiceImpl<ClothMapper, Cloth> implements ClothService {

    @Override
    public Cloth getByClothId(Long clothId) {
        QueryWrapper<Cloth> wrapper =new QueryWrapper<>();
        wrapper.eq("cloth_id", clothId);
        return super.getOne(wrapper);
    }

    @Override
    public List<Cloth> getByGender(String gender) {
        QueryWrapper<Cloth> wrapper =new QueryWrapper<>();
        wrapper.eq("gender", gender);
        return super.list(wrapper);
    }

    @Override
    public List<Cloth> getByBatchId(Long batchId) {
        QueryWrapper<Cloth> wrapper =new QueryWrapper<>();
        wrapper.eq("batch_id", batchId);
        return super.list(wrapper);
    }
}
