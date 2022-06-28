package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.mapper.ClothImgMapper;
import com.team14.sms.service.ClothImgService;
import com.team14.sms.vo.ClothImg;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-06-27
 */
@Service
public class ClothImgServiceImpl extends ServiceImpl<ClothImgMapper, ClothImg> implements ClothImgService {

    @Override
    public List<ClothImg> getByClothId(Long clothId) {
        QueryWrapper<ClothImg> wrapper =new QueryWrapper<>();
        wrapper.eq("cloth_id", clothId);
        return super.list(wrapper);
    }

    @Override
    public JsonResponse addState(ClothImg clothImg) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            try {
                super.save(clothImg);
                return JsonResponse.successMessage("添加成功");
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员！");
    }

}
