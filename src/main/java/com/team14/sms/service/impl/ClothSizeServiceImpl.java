package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.service.ClothService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.dao.ClothSize;
import com.team14.sms.mapper.ClothSizeMapper;
import com.team14.sms.service.ClothSizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClothSizeServiceImpl extends ServiceImpl<ClothSizeMapper, ClothSize> implements ClothSizeService {

    @Autowired
    private ClothService clothService;

    @Override
    public List<ClothSize> getByClothId(Long clothId) {
        QueryWrapper<ClothSize> wrapper =new QueryWrapper<>();
        wrapper.eq("cloth_id", clothId);
        return super.list(wrapper);
    }

    @Override
    public JsonResponse addState(ClothSize clothSize) {
        if (clothSize.getClothId() == null) {
            return JsonResponse.failure("添加失败，您未填写衣服商品号");
        }
        if (clothService.getByClothId(clothSize.getClothId()) == null) {
            return JsonResponse.failure("添加失败，衣服商品号不存在");
        }
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            try {
                QueryWrapper<ClothSize> wrapper = new QueryWrapper<>();
                wrapper.eq("cloth_id", clothSize.getClothId()).eq("cloth_size", clothSize.getClothSize());
                if (super.getOne(wrapper) != null) {
                    return JsonResponse.failure("添加失败，该衣服的" + clothSize.getClothSize() +"号已经存在");
                }
                super.save(clothSize);
                return JsonResponse.successMessage("添加成功");
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员！");
    }

}
