package com.wmj.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.*;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.mapper.StuApplyMapper;
import com.wmj.sms.service.*;
import com.wmj.sms.utls.SessionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class StuApplyServiceImpl extends ServiceImpl<StuApplyMapper, StuApply> implements StuApplyService {

    @Autowired
    private ClothService clothService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private ClothSizeService clothSizeService;
    @Autowired
    private ManagerService managerService;

    @Autowired
    private StuApplyMapper stuApplyMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private EnableService enableService;

    @Override
    public void exportState(HttpServletResponse response) {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("1")) { // 学校用户导出所有申请记录审核状态
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=exportState.xls");
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
            wrapper.eq("batch_id", curBatch.getBatchId());
            List<StuApply> stuApplies = super.list(wrapper);
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("学号");

            cell = row.createCell(1);
            cell.setCellValue("姓名");

            cell = row.createCell(2);
            cell.setCellValue("批次号");

            cell = row.createCell(3);
            cell.setCellValue("审核状态");

            for (int i = 0; i < stuApplies.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(stuApplies.get(i).getStuId());

                cell = row.createCell(1);
                Student student = studentService.getByStuId(stuApplies.get(i).getStuId());
                cell.setCellValue(student.getStuName());

                cell = row.createCell(2);
                cell.setCellValue(stuApplies.get(i).getBatchId());

                cell = row.createCell(3);
                if (stuApplies.get(i).getState() == 1) {
                    cell.setCellValue("学校同意申请");
                } else if (stuApplies.get(i).getState() == 2) {
                    cell.setCellValue("学院同意申请");
                } else if (stuApplies.get(i).getState() == 3) {
                    cell.setCellValue("辅导员同意申请");
                } else if (stuApplies.get(i).getState() == 4) {
                    cell.setCellValue("当前未审批");
                } else {
                    cell.setCellValue("已拒绝该申请");
                }
            }

            try {
                ((HSSFWorkbook) wb).write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (loginUser.getUserType().equals("2")) { // 学院用户导出自己学院学生的申请状态
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=exportState.xls");
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
            wrapper.eq("batch_id", curBatch.getBatchId()).eq("col_id", loginUser.getId());
            List<StuApply> stuApplies = super.list(wrapper);
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("学号");

            cell = row.createCell(1);
            cell.setCellValue("姓名");

            cell = row.createCell(2);
            cell.setCellValue("批次号");

            cell = row.createCell(3);
            cell.setCellValue("审核状态");

            for (int i = 0; i < stuApplies.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(stuApplies.get(i).getStuId());

                cell = row.createCell(1);
                Student student = studentService.getByStuId(stuApplies.get(i).getStuId());
                cell.setCellValue(student.getStuName());

                cell = row.createCell(2);
                cell.setCellValue(stuApplies.get(i).getBatchId());

                cell = row.createCell(3);
                if (stuApplies.get(i).getState() == 1) {
                    cell.setCellValue("学校同意申请");
                } else if (stuApplies.get(i).getState() == 2) {
                    cell.setCellValue("学院同意申请");
                } else if (stuApplies.get(i).getState() == 3) {
                    cell.setCellValue("辅导员同意申请");
                } else if (stuApplies.get(i).getState() == 4) {
                    cell.setCellValue("当前未审批");
                } else {
                    cell.setCellValue("已拒绝该申请");
                }
            }

            try {
                ((HSSFWorkbook) wb).write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  else if (loginUser.getUserType().equals("3")) { // 辅导员用户导出自己学生的申请状态
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=exportState.xls");
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
            wrapper.eq("batch_id", curBatch.getBatchId()).eq("man_id", loginUser.getId());
            List<StuApply> stuApplies = super.list(wrapper);
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("学号");

            cell = row.createCell(1);
            cell.setCellValue("姓名");

            cell = row.createCell(2);
            cell.setCellValue("批次号");

            cell = row.createCell(3);
            cell.setCellValue("审核状态");

            for (int i = 0; i < stuApplies.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(stuApplies.get(i).getStuId());

                cell = row.createCell(1);
                Student student = studentService.getByStuId(stuApplies.get(i).getStuId());
                cell.setCellValue(student.getStuName());

                cell = row.createCell(2);
                cell.setCellValue(stuApplies.get(i).getBatchId());

                cell = row.createCell(3);
                if (stuApplies.get(i).getState() == 1) {
                    cell.setCellValue("学校同意申请");
                } else if (stuApplies.get(i).getState() == 2) {
                    cell.setCellValue("学院同意申请");
                } else if (stuApplies.get(i).getState() == 3) {
                    cell.setCellValue("辅导员同意申请");
                } else if (stuApplies.get(i).getState() == 4) {
                    cell.setCellValue("当前未审批");
                } else {
                    cell.setCellValue("已拒绝该申请");
                }
            }

            try {
                ((HSSFWorkbook) wb).write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exportResult(HttpServletResponse response) {
        User loginUser = SessionUtils.getCurUser();
        // 学校用户按学院导出结果
        if (loginUser.getUserType().equals("1")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=exportResult.xls");
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            List<CountResult> stuApplies = stuApplyMapper.countResult(curBatch.getBatchId());
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("学院号");

            cell = row.createCell(1);
            cell.setCellValue("学院名");

            cell = row.createCell(2);
            cell.setCellValue("服装编号");

            cell = row.createCell(3);
            cell.setCellValue("服装名");

            cell = row.createCell(4);
            cell.setCellValue("服装尺码");

            cell = row.createCell(5);
            cell.setCellValue("数量");

            for (int i = 0; i < stuApplies.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(stuApplies.get(i).getColId());

                cell = row.createCell(1);
                QueryWrapper<College> collegeWrapper = new QueryWrapper<>();
                collegeWrapper.eq("col_id", stuApplies.get(i).getColId());
                College college = collegeService.getOne(collegeWrapper);
                cell.setCellValue(college.getColName());

                cell = row.createCell(2);
                cell.setCellValue(stuApplies.get(i).getClothId());

                cell = row.createCell(3);
                QueryWrapper<Cloth> clothWrapper = new QueryWrapper<>();
                clothWrapper.eq("cloth_id", stuApplies.get(i).getClothId());
                Cloth cloth = clothService.getOne(clothWrapper);
                cell.setCellValue(cloth.getClothName());

                cell = row.createCell(4);
                cell.setCellValue(stuApplies.get(i).getClothSize());

                cell = row.createCell(5);
                cell.setCellValue(stuApplies.get(i).getNum());
            }

            try {
                ((HSSFWorkbook) wb).write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (loginUser.getUserType().equals("2")) { // 学院用户按辅导员导出结果
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=exportResult.xls");
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            List<CountResultByManager> stuApplies = stuApplyMapper.countResultByManager(curBatch.getBatchId(), loginUser.getId());
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("辅导员号");

            cell = row.createCell(1);
            cell.setCellValue("辅导员名");

            cell = row.createCell(2);
            cell.setCellValue("服装编号");

            cell = row.createCell(3);
            cell.setCellValue("服装名");

            cell = row.createCell(4);
            cell.setCellValue("服装尺码");

            cell = row.createCell(5);
            cell.setCellValue("数量");

            for (int i = 0; i < stuApplies.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(stuApplies.get(i).getManId());

                cell = row.createCell(1);
                QueryWrapper<Manager> managerWrapper = new QueryWrapper<>();
                managerWrapper.eq("man_id", stuApplies.get(i).getManId());
                Manager manager = managerService.getOne(managerWrapper);
                cell.setCellValue(manager.getManName());

                cell = row.createCell(2);
                cell.setCellValue(stuApplies.get(i).getClothId());

                cell = row.createCell(3);
                QueryWrapper<Cloth> clothWrapper = new QueryWrapper<>();
                clothWrapper.eq("cloth_id", stuApplies.get(i).getClothId());
                Cloth cloth = clothService.getOne(clothWrapper);
                cell.setCellValue(cloth.getClothName());

                cell = row.createCell(4);
                cell.setCellValue(stuApplies.get(i).getClothSize());

                cell = row.createCell(5);
                cell.setCellValue(stuApplies.get(i).getNum());
            }

            try {
                ((HSSFWorkbook) wb).write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (loginUser.getUserType().equals("3")) { // 辅导员用户按学生导出结果
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=exportResult.xls");
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            List<CountResultByStudent> stuApplies = stuApplyMapper.countResultByStudent(curBatch.getBatchId(), loginUser.getId());
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);

            Cell cell = row.createCell(0);
            cell.setCellValue("学生号");

            cell = row.createCell(1);
            cell.setCellValue("学生名");

            cell = row.createCell(2);
            cell.setCellValue("服装编号");

            cell = row.createCell(3);
            cell.setCellValue("服装名");

            cell = row.createCell(4);
            cell.setCellValue("服装尺码");

            cell = row.createCell(5);
            cell.setCellValue("数量");

            for (int i = 0; i < stuApplies.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(stuApplies.get(i).getStuId());

                cell = row.createCell(1);
                QueryWrapper<Student> studentWrapper = new QueryWrapper<>();
                studentWrapper.eq("stu_id", stuApplies.get(i).getStuId());
                Student student = studentService.getOne(studentWrapper);
                cell.setCellValue(student.getStuName());

                cell = row.createCell(2);
                cell.setCellValue(stuApplies.get(i).getClothId());

                cell = row.createCell(3);
                QueryWrapper<Cloth> clothWrapper = new QueryWrapper<>();
                clothWrapper.eq("cloth_id", stuApplies.get(i).getClothId());
                Cloth cloth = clothService.getOne(clothWrapper);
                cell.setCellValue(cloth.getClothName());

                cell = row.createCell(4);
                cell.setCellValue(stuApplies.get(i).getClothSize());

                cell = row.createCell(5);
                cell.setCellValue(stuApplies.get(i).getNum());
            }

            try {
                ((HSSFWorkbook) wb).write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    // 需填入批次号、衣服号、衣服尺寸、申请原因（老生新生均可，但老生会检测）
    public JsonResponse apply(StuApply stuApply) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学生可以提交申请
        if (loginUser.getUserType().equals("4")) {
            if (enableService.getByStuId(loginUser.getId()) == null) {
                return JsonResponse.failure("您非贫困学生，请联系辅导员添加");
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

    @Override
    public JsonResponse deleteApply(StuApply stuApply) {
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
        wrapper.eq("id", stuApply.getId());
        super.remove(wrapper);
        return JsonResponse.success("删除成功");
    }
    @Override
    public JsonResponse deleteApplyByStuId(StuApply stuApply) {
        User loginUser = SessionUtils.getCurUser();
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id", stuApply.getStuId());
        super.remove(wrapper);
        return JsonResponse.success("删除成功");
    }

    @Override
    // 需填入id, 批次号、衣服号、衣服尺寸、申请原因（老生新生均可，但老生会检测）
    public JsonResponse studentModify(StuApply stuApply) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学生可以修改自己的申请记录
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

                if (!clothService.getByClothId((stuApply.getClothId())).getGender().
                        equals(studentService.getByStuId(loginUser.getId()).getGender())) {
                    return JsonResponse.failure("请选择属于您性别的衣服 ");
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
                        return JsonResponse.failure("修改失败，请填写申请理由");
                    }
                }

                // 如果入学时间晚于批次年份，则返回错误
                if (enYear.get(Calendar.YEAR) > batchYear.get(Calendar.YEAR)) {
                    return JsonResponse.failure("修改失败，您非该批次准许学生，该批次早于您的入学时间");
                }
                // 老生填写了申请理由或新生无需检测
                stuApply.setScDate(new Date());
                stuApply.setRefReason(null);
                stuApply.setState(4L); // 重置状态和拒绝理由
                QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
                wrapper.eq("id", stuApply.getId());
                super.update(stuApply, wrapper);
                return JsonResponse.success(stuApply, "修改成功，请联系辅导员重新审核");
            }
        }
        return JsonResponse.failure("您非本校学生，无修改寒衣申请补助的权限，请联系系统管理员");
    }

    @Override
    // 需填入批次号、衣服号、衣服尺寸
    public JsonResponse schoolModify(StuApply stuApply) {
        User loginUser = SessionUtils.getCurUser();
        // 仅学校可以修改学生申请
        if (loginUser.getUserType().equals("1")) {
            // 信息未填写完毕
            if (stuApply.getId() == null || stuApply.getClothId() == null || StringUtils.isBlank(stuApply.getClothSize())) {
                return JsonResponse.failure("请检查您是否填写了记录id、衣服商品号或衣服尺码");
            } else {
                // 填写的衣服不存在
                if (clothService.getByClothId(stuApply.getClothId()) == null) {
                    return JsonResponse.failure("您填写的衣服商品号不存在，请重新填写");
                }

                // 当前批次中无本衣服
                QueryWrapper<Cloth> clothQueryWrapper = new QueryWrapper<>();
                clothQueryWrapper.eq("cloth_id", stuApply.getClothId()).
                        eq("batch_id", stuApply.getBatchId());
                if (clothService.getOne(clothQueryWrapper) == null) {
                    return JsonResponse.failure("该衣服不属于申请记录的批次");
                }

                // 衣服尺码填写错误
                QueryWrapper<ClothSize> sizeQueryWrapper = new QueryWrapper<>();
                sizeQueryWrapper.eq("cloth_id", stuApply.getClothId()).eq("cloth_size", stuApply.getClothSize());
                if (clothSizeService.getOne(sizeQueryWrapper) == null) {
                    return JsonResponse.failure("您填写的衣服尺码不存在，请重新填写");
                }

                // 前序检测无问题，进入修改申请阶段
                stuApply.setScDate(new Date());
                stuApply.setRefReason(null);
                stuApply.setState(4L); // 重置状态和拒绝理由
                QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
                wrapper.eq("id", stuApply.getId());
                super.update(stuApply, wrapper);
                return JsonResponse.success(stuApply, "修改成功");
            }
        }
        return JsonResponse.failure("您无修改学生寒衣申请补助记录的权限，请联系系统管理员");
    }

    // 学生查询自己的申请记录
    @Override
    public Page<StuApply> studentSelect(PageDTO pageDTO) {
        if (batchService.getCurBatch().getData() == null) {
            return null;
        }
        User loginUser = SessionUtils.getCurUser();
        Page<StuApply> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
        Batch batch = (Batch) batchService.getCurBatch().getData();
        if (!loginUser.getUserType().equals("4") || batch == null) {
            return null;
        } else {
            wrapper.eq("stu_id", loginUser.getId());
            wrapper.eq("batch_id", batch.getBatchId());
            page = super.page(page, wrapper);
            return page;
        }
    }

    // 根据学院号、辅导员号查询所属学生中未审核的记录
    @Override
    public Page<StuApply> selectNotExamineStuApply(PageDTO pageDTO) {

        if (batchService.getNotExamineBatch().getData() == null) {
            return null;
        }
        User loginUser = SessionUtils.getCurUser();
        Page<StuApply> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
        Batch batch = (Batch) batchService.getNotExamineBatch().getData();

        switch (loginUser.getUserType()) {
            // 辅导员
            case "3":
                wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
                wrapper.eq("man_id", loginUser.getId());
                wrapper.eq("state", 4); // 未审核
                page = super.page(page, wrapper);
                // System.out.println(stuApplyPage);
                break;
            // 学院
            case "2":
                wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
                wrapper.eq("col_id", loginUser.getId());
                wrapper.eq("state", 3); // 辅导员审核通过
                page = super.page(page, wrapper);
                break;
            // 学校
            case "1":
                wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
                wrapper.eq("state", 2); // 学院审核通过
                page = super.page(page, wrapper);
                break;
        }
        return page;
    }

    // 根据学院号、辅导员号查询所属学生中未审核的记录
    @Override
    public Page<StuApply> selectAllStuApply(PageDTO pageDTO) {

        if (batchService.getNotExamineBatch().getData() == null) {
            return null;
        }
        User loginUser = SessionUtils.getCurUser();
        Page<StuApply> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
        Batch batch = (Batch) batchService.getNotExamineBatch().getData();
        wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
        page = super.page(page, wrapper); // 获取所有当前批次下学生申请记录
        return page;
    }

    // 根据学院号、辅导员号查询所属学生已未审核的记录
    @Override
    public Page<StuApply> selectExaminedStuApply(PageDTO pageDTO) {

        if (batchService.getNotExamineBatch().getData() == null) {
            return null;
        }
        User loginUser = SessionUtils.getCurUser();
        Page<StuApply> page = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
        Batch batch = (Batch) batchService.getNotExamineBatch().getData();

        switch (loginUser.getUserType()) {
            // 辅导员
            case "3":
                wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
                wrapper.eq("man_id", loginUser.getId());
                wrapper.ne("state", 0).ne("state", 4);
                page = super.page(page, wrapper);
                // System.out.println(stuApplyPage);
                break;
            // 学院
            case "2":
                wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
                wrapper.eq("col_id", loginUser.getId());
                wrapper.ne("state", 0).ne("state", 4).ne("state", 3);
                page = super.page(page, wrapper);
                break;
            // 学校
            case "1":
                wrapper.eq("batch_id", batch.getBatchId()); // 待审核批次
                wrapper.ne("state", 0).ne("state", 4)
                        .ne("state", 3).ne("state", 2);
                page = super.page(page, wrapper); // 学院审核通过
                break;
        }
        return page;
    }

    @Override
    public boolean agreeBatch(List<Long> idList) {
        User loginUser = SessionUtils.getCurUser();
        List<String> userType = new ArrayList<>();
        userType.add("1"); // 学校
        userType.add("2"); // 学院
        userType.add("3"); // 辅导员

        if (idList == null) {
            return false;
        } else {
            if (userType.contains(loginUser.getUserType())) {
                for (Long id : idList) {
                    QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
                    StuApply stuApply = super.getById(id);

                    if (stuApply == null || stuApply.getState().equals(0L)) {
                        continue;
                    } else {
                        switch (loginUser.getUserType()) {
                            case "1":
                                stuApply.setState(1L); // 学校通过
                                wrapper.eq("id", id);
                                super.update(stuApply, wrapper);
                                break;
                            case "2":
                                stuApply.setState(2L);// 学院通过
                                wrapper.eq("id", id);
                                super.update(stuApply, wrapper);
                                break;
                            case "3":
                                stuApply.setState(3L);// 辅导员通过
                                wrapper.eq("id", id);
                                super.update(stuApply, wrapper);
                                break;
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean disagreeBatch(List<StuApply> list) {
        // System.out.println(idList);
        User loginUser = SessionUtils.getCurUser();
        List<String> userType = new ArrayList<>();
        userType.add("1"); // 学校
        userType.add("2"); // 学院
        userType.add("3"); // 辅导员

        if (list == null) {
            return false;
        } else {
            if (userType.contains(loginUser.getUserType())) {
                for (StuApply temp : list) {
                    QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
                    StuApply stuApply = super.getById(temp.getId());
                    if (stuApply == null) {
                        continue;
                    } else {
                        // System.out.println(stuApply.getId());
                        stuApply.setState(0L);// 拒绝
                        if (temp.getRefReason() != null) {
                            stuApply.setRefReason(temp.getRefReason());
                        }
                        wrapper.eq("id", temp.getId());
                        super.update(stuApply, wrapper);
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public JsonResponse getStateByStu() {
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("4")) {
            Batch curBatch = (Batch) batchService.getNotExamineBatch().getData(); // 获取当前待审批批次
            QueryWrapper<StuApply> wrapper = new QueryWrapper<>();
            wrapper.eq("batch_id", curBatch.getBatchId()).eq("stu_id", loginUser.getId());
            List<StuApply> appList = super.list(wrapper);
            if (appList == null) {
            return JsonResponse.failure("您无申请记录");
            }
            return JsonResponse.success(appList, "查询成功");
        }
        return JsonResponse.failure("您无本操作权限，请联系系统管理员");
    }
}
