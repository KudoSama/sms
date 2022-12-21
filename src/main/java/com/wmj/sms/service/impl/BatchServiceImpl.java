package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.*;
import com.wmj.sms.mapper.*;
import com.wmj.sms.service.BatchService;
import com.wmj.sms.service.FileService;
import com.wmj.sms.utls.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-12-26
 */
@Service
public class BatchServiceImpl extends ServiceImpl<BatchMapper, Batch> implements BatchService {

    @Autowired
    private StuApplyMapper stuApplyMapper;

    @Autowired
    private ClothMapper clothMapper;

    @Autowired
    private BatchMapper batchMapper;

    @Autowired
    private ClothSizeMapper clothSizeMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private ClothImgMapper clothImgMapper;

    @Value("${file-upload-path}")
    private String fileUploadPath;// ./file   当前项目路径下的文件夹file

    @Override
    public JsonResponse modifyBatch(Batch batch) {
        QueryWrapper<Batch> wrapper =new QueryWrapper<>();
        wrapper.eq("batch_id", batch.getBatchId());
        User loginUser = SessionUtils.getCurUser();
        // System.out.println(loginUser);
        // 只有学校用户才能修改批次
        if (loginUser.getUserType().equals("1")) {
            // 未填写开始时间或结束时间
            if (batch.getBatchDateend() == null || batch.getBatchDatestart() == null) {
                return JsonResponse.failure("修改失败，您未填写开始日期或结束时间");
            } else {
                // 结束时间小于开始时间
                if (batch.getBatchDateend().compareTo(batch.getBatchDatestart()) <= 0) {
                    return JsonResponse.failure("修改失败，您填写的结束日期早于开始日期");
                } else {
                    Batch temp = super.getOne(wrapper);
                    if (temp == null) {
                        return JsonResponse.failure("修改失败，不存在该批次");
                    }
                    temp.setBatchDatestart(batch.getBatchDatestart());
                    temp.setBatchDateend(batch.getBatchDateend());
                    super.update(temp, wrapper);
                    return JsonResponse.successMessage("修改成功");
                }
            }
        }
        return JsonResponse.failure("修改失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public  JsonResponse deleteBatch(Batch batch) {
        QueryWrapper<Batch> wrapper =new QueryWrapper<>();
        wrapper.eq("batch_id", batch.getBatchId());
        User loginUser = SessionUtils.getCurUser();
        // System.out.println(loginUser);
        // 只有学校用户才能修改批次
        if (loginUser.getUserType().equals("1")) {
            Batch temp = super.getOne(wrapper);
            if (temp == null) {
                return JsonResponse.failure("删除失败，不存在该批次");
            }

            // 删除衣服、尺码以及图片
            QueryWrapper<Cloth> applyWrapper = new QueryWrapper<>();
            applyWrapper.eq("batch_id", batch.getBatchId());
            List<Cloth> clothList = clothMapper.selectList(applyWrapper); // 获取衣服列表

            for (Cloth cloth: clothList) {
                Long clothId = cloth.getClothId();
                // 删除尺寸
                clothSizeMapper.deleteByClothId(clothId);
                QueryWrapper<ClothImg> clothImgWrapper = new QueryWrapper<>();
                clothImgWrapper.eq("cloth_id", clothId);
                // 删除图片
                List<ClothImg> clothImgList = clothImgMapper.selectList(clothImgWrapper);
                for (ClothImg clothImg: clothImgList) {
                    fileService.delete(clothImg.getClothImg());
                }
                // 删除图片记录
                clothImgMapper.deleteByClothId(clothId);
            }

            // 删除申请记录
            stuApplyMapper.deleteByBatchId(temp.getBatchId());

            // 删除衣服
            clothMapper.deleteByBatchId(batch.getBatchId());

            // 删除批次
            batchMapper.deleteByBatchId(temp.getBatchId());
            return JsonResponse.successMessage("删除成功");
        }
        return JsonResponse.failure("删除失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse addState(Batch batch) {
        User loginUser = SessionUtils.getCurUser();
        // System.out.println(loginUser);
        // 只有学校用户才能添加批次
        if (loginUser.getUserType().equals("1")) {
            try {
                // 未填写批次号、开始时间或结束时间
                if (batch.getBatchId() == null || batch.getBatchDateend() == null ||
                        batch.getBatchDatestart() == null) {
                    return JsonResponse.failure("添加失败，您未填写批次号、开始日期或结束时间");
                } else {
                    QueryWrapper<Batch> wrapper = new QueryWrapper<>();
                    wrapper.eq("batch_id", batch.getBatchId());
                    if (super.getOne(wrapper) != null) {
                        return JsonResponse.failure(batch.getBatchId() + "批次已经存在，请填写新的批次号");
                    }
                    // 结束时间小于开始时间
                    if (batch.getBatchDateend().compareTo(batch.getBatchDatestart()) <= 0 ) {
                        return JsonResponse.failure("添加失败，您填写的结束日期早于开始日期");
                    } else {
                        super.save(batch);
                        return JsonResponse.successMessage("添加成功");
                    }
                }
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员!");
    }

    @Override
    public JsonResponse getCurBatch() {
        Date curDate = new Date();
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();
        wrapper.le("batch_dateStart", curDate).ge("batch_dateEnd", curDate);
        Batch curBatch = super.getOne(wrapper);
        if (curBatch == null) {
            return JsonResponse.failure("当前不属于申请阶段");
        }
        return JsonResponse.success(curBatch, "查询成功");
    }

    @Override
    public JsonResponse getBatchList() {
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();
        List<Batch> batchList = batchMapper.selectList(wrapper);
        return JsonResponse.success(batchList, "查询成功");
    }

    @Override
    public JsonResponse getNotExamineBatch() {
        Date curDate = new Date();
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();

        // 查找结束时间早于等于今天，且今天早于等于批次结束日期的下个月
        wrapper.ge("batch_dateEnd", curDate);
        List<Batch> notExamineBatchList = super.list(wrapper);// 查询到比当前时间早结束的批次列表

        Batch notExamineBatch = null;
        Date notExamineDate;
        for (Batch batch: notExamineBatchList) {
            Calendar temp = Calendar.getInstance();
            temp.setTime(batch.getBatchDateend());
            temp.add(Calendar.MONTH, 1); // 获取批次结束后一个月的时间
            notExamineDate = temp.getTime();

            if (notExamineDate.after(curDate)) {
                notExamineBatch = batch;
                break;
            }
        }

        if (notExamineBatch == null) {
            return JsonResponse.failure("当前不属于审批阶段");
        }
        return JsonResponse.success(notExamineBatch, "查询成功");
    }

    @Override
    public Batch getByBatchId (Long batchId) {
        QueryWrapper<Batch> wrapper = new QueryWrapper<>();
        wrapper.eq("batch_id", batchId);
        return super.getOne(wrapper);
    }
}
