package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.service.ClothService;
import com.wmj.sms.mapper.ClothImgMapper;
import com.wmj.sms.service.ClothImgService;
import com.wmj.sms.dao.ClothImg;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
@Service
public class ClothImgServiceImpl extends ServiceImpl<ClothImgMapper, ClothImg> implements ClothImgService {

    @Autowired
    private ClothService clothService;

    @Autowired
    private ClothImgService clothImgService;

    @Autowired
    private ClothImgMapper clothImgMapper;

    @Autowired
    private FileService fileService;


    @Override
    public List<ClothImg> getByClothId(Long clothId) {
        QueryWrapper<ClothImg> wrapper = new QueryWrapper<>();
        wrapper.eq("cloth_id", clothId);
        return super.list(wrapper);
    }

    @Override
    public JsonResponse addState(ClothImg clothImg) {
        if (clothImg.getClothId() == null) {
            return JsonResponse.failure("上传失败，您未填写衣服商品号");
        }
        if (clothImgService.getByClothId(clothImg.getClothId()) == null) {
            return JsonResponse.failure("上传失败，衣服商品号不存在");
        }
//        System.out.println(clothImg);
        try {
            super.save(clothImg);
            return JsonResponse.successMessage("上传成功");
        } catch (Exception e) {
            return JsonResponse.failure("上传失败");
        }
    }

    @Override
    public JsonResponse deleteState(ClothImg clothImg) {
        if (clothImg.getClothId() == null) {
            return JsonResponse.failure("删除失败，您未填写衣服商品号");
        }
        if (clothImgService.getByClothId(clothImg.getClothId()) == null) {
            return JsonResponse.failure("删除失败，衣服商品号不存在");
        }
        // 删除图片
        fileService.delete(clothImg.getClothImg());
        // 删除图片记录
        clothImgMapper.deleteByClothImg(clothImg.getClothImg());
        return JsonResponse.successMessage("删除成功");
    }
}
