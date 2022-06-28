package com.team14.sms.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ClothImgMapper;
import com.team14.sms.service.ClothImgService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.ClothImg;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.team14.sms.base.BaseController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lmh
 * @since 2022-06-27
 */
@Controller
@RequestMapping("/api/clothImg")
public class ClothImgController extends BaseController {


    @Autowired
    private ClothImgService clothImgService;

    @Autowired
    private ClothImgMapper clothImgMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加服装图片接口",notes = "应传入：clothId,clothImg")
    public JsonResponse addClothSize(@RequestBody @Valid ClothImg clothImg){
        return clothImgService.addState(clothImg);
    }

    @RequestMapping(value = "/getClothImgByClothId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装号查询接口",notes = "应传入：clothId")
    @ApiImplicitParam(name = "clothId", value = "服装编号", required = true, dataType = "Long",dataTypeClass = Long.class)
    public JsonResponse getClothImgById(Long clothId){
        List<ClothImg> cloth_img_list = clothImgService.getByClothId(clothId);
        return JsonResponse.success(cloth_img_list, "查询成功");
    }

}
