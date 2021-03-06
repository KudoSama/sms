package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.service.ClothService;
import com.team14.sms.mapper.ClothImgMapper;
import com.team14.sms.service.ClothImgService;
import com.team14.sms.dao.ClothImg;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-06-27
 */
@Service
public class ClothImgServiceImpl extends ServiceImpl<ClothImgMapper, ClothImg> implements ClothImgService {

    @Autowired
    private ClothService clothService;

    @Autowired
    private ClothImgService clothImgService;


    @Override
    public List<ClothImg> getByClothId(Long clothId) {
        QueryWrapper<ClothImg> wrapper = new QueryWrapper<>();
        wrapper.eq("cloth_id", clothId);
        return super.list(wrapper);
    }

    @Override
    public JsonResponse addState(ClothImg clothImg) {
        if (clothImg.getClothId() == null) {
            return JsonResponse.failure("添加失败，您未填写衣服商品号");
        }
        if (clothImgService.getByClothId(clothImg.getClothId()) == null) {
            return JsonResponse.failure("添加失败，衣服商品号不存在");
        }
        System.out.println(clothImg);
        try {
            super.save(clothImg);
            return JsonResponse.successMessage("添加成功");
        } catch (Exception e) {
            return JsonResponse.failure("添加失败");
        }
    }

}
