package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ClothMapper;
import com.team14.sms.service.ClothService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Cloth;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import com.team14.sms.base.BaseController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@Controller
@RequestMapping("/api/cloth")
public class ClothController extends BaseController {


    @Autowired
    private ClothService clothService;

    @Autowired
    private ClothMapper clothMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加服装接口",notes = "应传入：clothId,clothName,gender,batchId")
    public JsonResponse addCloth(@RequestBody @Valid Cloth cloth){
        User loginUser = SessionUtils.getCurUser();
        if (loginUser.getUserType().equals("school")) {
            try {
                clothService.save(cloth);
                return JsonResponse.successMessage("添加成功");
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员！");

    }

    @RequestMapping(value = "/getClothByGender", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装性别接口",notes = "应传入gender：男、女（字符串）")
    @ApiImplicitParam(name = "gender", value = "性别", required = true, dataType = "String",dataTypeClass = String.class)
    public JsonResponse clothGender(String gender){
        // System.out.println(gender);
        List<Cloth> cloth_list = clothService.getByGender(gender);
        return JsonResponse.success(cloth_list, "查询成功");
    }

    @RequestMapping(value = "/getClothByBatchId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装批次接口",notes = "应传入：batchId")
    @ApiImplicitParam(name = "batchId", value = "批次号", required = true, dataType = "Long",dataTypeClass = Long.class)
    public JsonResponse clothBatch(Long batchId){
        // System.out.println(batchId);
        List<Cloth> cloth_list =clothService.getByBatchId(batchId);
        return JsonResponse.success(cloth_list, "查询成功");
    }

}
