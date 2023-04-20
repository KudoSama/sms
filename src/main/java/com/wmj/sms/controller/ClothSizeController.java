package com.wmj.sms.controller;


import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.ClothSizeMapper;
import com.wmj.sms.service.ClothSizeService;
import com.wmj.sms.dao.ClothSize;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import com.wmj.sms.base.BaseController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
@Controller
@RequestMapping("/api/clothSize")
public class ClothSizeController extends BaseController {


    @Autowired
    private ClothSizeService clothSizeService;

    @Autowired
    private ClothSizeMapper clothSizeMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加服装尺寸接口",notes = "应传入：clothId,clothSize")
    public JsonResponse addClothSize(@RequestBody @Valid ClothSize clothSize){
        return clothSizeService.addState(clothSize);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除服装尺寸接口",notes = "应传入：clothId,clothSize")
    public JsonResponse deleteClothSize(@RequestBody @Valid ClothSize clothSize){
        return clothSizeService.deleteState(clothSize);
    }

    @RequestMapping(value = "/getClothSizeByClothId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装号查询接口",notes = "应传入clothId")
    @ApiImplicitParam(name = "clothId", value = "服装编号", required = true, dataType = "Long",dataTypeClass = Long.class)
    public JsonResponse getClothSizeById(Long clothId){
        List<ClothSize> cloth_size_list = clothSizeService.getByClothId(clothId);
        return JsonResponse.success(cloth_size_list, "查询成功");
    }
}
