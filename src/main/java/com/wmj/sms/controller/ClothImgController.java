package com.wmj.sms.controller;

import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.mapper.ClothImgMapper;
import com.wmj.sms.service.ClothImgService;
import com.wmj.sms.dao.ClothImg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import com.wmj.sms.base.BaseController;

import javax.validation.Valid;
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
@RequestMapping("/api/clothImg")
public class ClothImgController extends BaseController {


    @Autowired
    private ClothImgService clothImgService;

    @Autowired
    private ClothImgMapper clothImgMapper;

    @RequestMapping(value = "/getClothImgByClothId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装号查询接口",notes = "应传入：clothId")
    @ApiImplicitParam(name = "clothId", value = "服装编号", required = true, dataType = "Long",dataTypeClass = Long.class)
    public JsonResponse getClothImgById(Long clothId){
        List<ClothImg> cloth_img_list = clothImgService.getByClothId(clothId);
        return JsonResponse.success(cloth_img_list, "查询成功");
    }

    @RequestMapping(value = "/upload", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存衣服对应图片路径",notes = "上传clothId，clothImg")
    public JsonResponse upload(@RequestBody @Valid ClothImg clothImg) {
        return clothImgService.addState(clothImg);
    }

    @RequestMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存衣服对应图片路径",notes = "上传clothId，clothImg")
    public JsonResponse delete(@RequestBody @Valid ClothImg clothImg) {
        return clothImgService.deleteState(clothImg);
    }

}
