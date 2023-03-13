package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.service.ClothService;
import com.wmj.sms.utls.SessionUtils;
import com.wmj.sms.dao.ClothSize;
import com.wmj.sms.mapper.ClothSizeMapper;
import com.wmj.sms.service.ClothSizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
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

    @Override
    public JsonResponse deleteState(ClothSize clothSize) {
        if (clothSize.getClothId() == null) {
            return JsonResponse.failure("添加失败，您未填写衣服商品号");
        }
        if (clothService.getByClothId(clothSize.getClothId()) == null) {
            return JsonResponse.failure("添加失败，衣服商品号不存在");
        }
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            QueryWrapper<ClothSize> wrapper = new QueryWrapper<>();
            wrapper.eq("cloth_id", clothSize.getClothId()).eq("cloth_size", clothSize.getClothSize());
            super.remove(wrapper);
            return JsonResponse.successMessage("删除成功");
        }
        return JsonResponse.failure("删除失败，您无本操作权限，请联系系统管理员！");
    }

}
