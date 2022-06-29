package com.team14.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.service.*;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.*;
import com.team14.sms.mapper.StuApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wmj
 * @since 2022-06-27
 */
@Service
public class StuApplyServiceImpl extends ServiceImpl<StuApplyMapper, StuApply> implements StuApplyService {

    @Autowired
    private ClothService clothService;

    @Autowired
    private ClothSizeService clothSizeService;
    @Autowired
    private ManagerService managerService;


    @Autowired
    private StudentService studentService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private EnableService enableService;

    @Override
    // 需填入批次号、衣服号、衣服尺寸、申请原因（老生新生均可，但老生会检测）
    public JsonResponse apply(StuApply stuApply) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学生可以提交申请
        if (loginUser.getUserType().equals("4")) {
            if (enableService.getByStuId(loginUser.getId()) == null) {
                return JsonResponse.failure("您非贫困学生，请联系辅导员");
            }
            // 信息未填写完毕
            if (stuApply.getBatchId() == null || stuApply.getClothId() == null || StringUtils.isBlank(stuApply.getClothSize())) {
                return JsonResponse.failure("请检查您是否填写了批次、衣服商品号或衣服尺码");
            } else {
                // 填写的衣服不存在
                if (clothService.getByClothId(stuApply.getClothId()) == null) {
                    return JsonResponse.failure("您填写的衣服商品号不存在，请重新填写");
                }

                // 批次号填写错误
                if (batchService.getByBatchId(stuApply.getBatchId()) == null) {
                    return JsonResponse.failure("您填写的批次不存在，请重新填写");
                }

                // 当前非提交申请时间
                if (batchService.getByBatchId(stuApply.getBatchId()).getBatchDateend().before(new Date())) {
                    return JsonResponse.failure("当前日期非该批次申请时间");
                }

                // 填写的批次号错误
                if (!stuApply.getBatchId().equals(clothService.getByClothId(stuApply.getClothId()).getBatchId())) {
                    return JsonResponse.failure("您填写的批次号与当前仓库中衣服的批次号不对应");
                }

                // 衣服尺码填写错误
                QueryWrapper<ClothSize> sizeQueryWrapper = new QueryWrapper<>();
                sizeQueryWrapper.eq("cloth_id", stuApply.getClothId()).eq("cloth_size", stuApply.getClothSize());
                if (clothSizeService.getOne(sizeQueryWrapper) == null) {
                    return JsonResponse.failure("您填写的衣服尺码不存在，请重新填写");
                }

                // 前序检测无问题，进入提交申请阶段
                Calendar enYear = Calendar.getInstance();
                // 获取入学年份
                enYear.setTime(studentService.getByStuId(loginUser.getId()).getEnDate());

                Calendar batchYear = Calendar.getInstance();
                // 获取批次年份
                batchYear.setTime(batchService.getByBatchId(stuApply.getBatchId()).getBatchDatestart());

                // 如果入学年份小于批次年份，即学生为老生
                if (enYear.get(Calendar.YEAR) < batchYear.get(Calendar.YEAR)) {
                    // 如果老生未填写申请理由，则返回提交申请失败
                    if (StringUtils.isBlank(stuApply.getAppReason())) {
                        return JsonResponse.failure("申请失败，请填写申请理由");
                    }
                }

                // 如果入学时间晚于批次年份，则返回错误
                if (enYear.get(Calendar.YEAR) > batchYear.get(Calendar.YEAR)) {
                    return JsonResponse.failure("申请失败，您非该批次准许学生，该批次早于您的入学时间");
                }
                // 老生填写了申请理由或新生无需检测
                Student student = studentService.getByStuId(loginUser.getId());
                stuApply.setManId(student.getManId()); // 添加辅导员号
                Manager manager = managerService.getByManId(student.getManId());
                stuApply.setColId(manager.getColId()); // 添加学院号
                stuApply.setStuId(loginUser.getId());
                stuApply.setState(4L); // 未审批状态
                stuApply.setScDate(new Date());
                super.save(stuApply);
                return JsonResponse.success(stuApply, "申请成功，请等待审批结果");
            }
        }
        return JsonResponse.failure("您非本校学生，无提交寒衣申请补助的权限，请联系系统管理员");
    }


    // 根据学生id查询学生的所有申请记录
    @Override
    public Page<StuApply> selectStuApply(PageDTO pageDTO) {
        User loginUser = SessionUtils.getCurUser();
        Page<StuApply> page = new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();

        // 辅导员
        if (loginUser.getUserType().equals("3")) {
            wrapper.eq("man_id", loginUser.getId());
            wrapper.eq("state", 4);
            page = super.page(page, wrapper);
            // System.out.println(stuApplyPage);
        }
        // 学院
        else if (loginUser.getUserType().equals("2")) {
            wrapper.eq("col_id", loginUser.getId());
            wrapper.eq("state", 3);
            page = super.page(page, wrapper);
        }
        else if (loginUser.getUserType().equals("1")) {
            wrapper.eq("state", 2);
            page = super.page(page, wrapper);
        }
        return page;
    }
}
