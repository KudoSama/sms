package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.service.BatchService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.dao.Cloth;
import com.team14.sms.mapper.ClothMapper;
import com.team14.sms.service.ClothService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team14.sms.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@Service
public class ClothServiceImpl extends ServiceImpl<ClothMapper, Cloth> implements ClothService {

    @Autowired
    private BatchService batchService;

    @Override
    public Cloth getByClothId(Long clothId) {
        QueryWrapper<Cloth> wrapper = new QueryWrapper<>();
        wrapper.eq("cloth_id", clothId);
        return super.getOne(wrapper);
    }

    @Override
    public Page<Cloth> getByGender(String gender, PageDTO pageDTO) {
        Page<Cloth> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<Cloth> wrapper = new QueryWrapper<>();
        wrapper.eq("gender", gender);
        page = super.page(page, wrapper);
        return page;
    }

    @Override
    public Page<Cloth> getByBatchId(Long batchId, PageDTO pageDTO) {
        Page<Cloth> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<Cloth> wrapper = new QueryWrapper<>();
        wrapper.eq("batch_id", batchId);
        page = super.page(page, wrapper);
        return page;
    }

    @Override
    public JsonResponse addState(Cloth cloth) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) {
            try {
                if (cloth.getClothId() == null || cloth.getBatchId() == null || cloth.getGender() == null) {
                    return JsonResponse.failure("请见窜是否已经填写了衣服商品号、批次号或性别");
                }
                if (batchService.getByBatchId(cloth.getBatchId()) == null) {
                    return JsonResponse.failure("不存在该批次号，请检查填写的信息是否正确");
                }
                QueryWrapper<Cloth> wrapper = new QueryWrapper<>();
                wrapper.eq("cloth_id", cloth.getClothId()).eq("batch_id", cloth.getBatchId());
                if (super.getOne(wrapper) != null) {
                    return JsonResponse.failure(cloth.getBatchId() + "批次中已经存在" + cloth.getClothId() +
                            "号衣服，请重新检查衣服信息和批次号");
                }
                super.save(cloth);
                return JsonResponse.successMessage("添加成功");
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员！");
    }
}
