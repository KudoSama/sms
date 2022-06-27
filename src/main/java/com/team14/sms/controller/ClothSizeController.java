package com.team14.sms.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.ClothSizeMapper;
import com.team14.sms.service.ClothSizeService;
import com.team14.sms.utls.SessionUtils;
import com.team14.sms.vo.ClothSize;
import com.team14.sms.vo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @author lmh
 * @since 2022-06-27
 */
@Controller
@RequestMapping("/api/clothsize")
public class ClothSizeController extends BaseController {


    @Autowired
    private ClothSizeService clothSizeService;

    @Autowired
    private ClothSizeMapper clothSizeMapper;

//    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    @ApiOperation(value = "添加服装尺寸接口",notes = "应传入：clothId,clothSize")
//    public JsonResponse addClothSize(@RequestBody @Valid ClothSize clothSize){
//
//    }

}
