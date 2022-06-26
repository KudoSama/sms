package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ClothMapper;
import com.team14.sms.service.ClothService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.Cloth;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.team14.sms.base.BaseController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/cloth")
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
        if (loginUser.getUserType().equals("schoool")) {
            try {
                clothService.save(cloth);
                return JsonResponse.successMessage("添加成功");
            } catch (Exception e) {
                return JsonResponse.failure("添加失败");
            }
        }
        return JsonResponse.failure("添加失败，您无本操作权限，请联系系统管理员！");

    }

}
