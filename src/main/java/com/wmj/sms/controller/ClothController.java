package com.wmj.sms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wmj.sms.base.JsonResponse;
import com.wmj.sms.dao.Batch;
import com.wmj.sms.dao.ClothImg;
import com.wmj.sms.dao.School;
import com.wmj.sms.dto.PageDTO;
import com.wmj.sms.mapper.ClothMapper;
import com.wmj.sms.service.BatchService;
import com.wmj.sms.service.ClothImgService;
import com.wmj.sms.service.ClothService;
import com.wmj.sms.dao.Cloth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wmj.sms.base.BaseController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmj
 * @since 2022-12-26
 */
@Controller
@RequestMapping("/api/cloth")
public class ClothController extends BaseController {


    @Autowired
    private ClothService clothService;

    @Autowired
    private ClothMapper clothMapper;

    @Autowired
    private BatchService batchService;

    @Autowired
    private ClothImgService clothImgService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加服装接口",notes = "应传入：clothId,clothName,gender,batchId")
    public JsonResponse addCloth(@RequestBody @Valid Cloth cloth){
        return clothService.addState(cloth);
    }

    @RequestMapping(value = "/getClothByGender", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装性别接口",notes = ("应传入pageNo, pageSize"))
    public JsonResponse getClothByGender(@RequestBody @Valid PageDTO pageDTO){
        if (clothService.getByGender(pageDTO) == null) {
            return JsonResponse.failure("当前不属于申请时间，无衣物展示");
        }
        Page<Cloth> clothPage = clothService.getByGender(pageDTO);
        JsonResponse jsonResponse = new JsonResponse();
        for (Cloth cloth : clothPage.getRecords()) {
            List<String> clothImgs = new ArrayList<>();
            for (ClothImg clothImg : clothImgService.getByClothId(cloth.getClothId())) {
                clothImgs.add(clothImg.getClothImg());
            }
            jsonResponse.addOtherData(String.valueOf(cloth.getClothId()), clothImgs);
        }
        jsonResponse.setData(clothPage);
        jsonResponse.setMessage("查询成功");
        jsonResponse.setStatus(true);
        return jsonResponse;
    }

    @RequestMapping(value = "/getClothByBatchId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装批次接口",notes = "应传入：pageNo, pageSize")
    public JsonResponse getClothByBatchId(@RequestBody @Valid PageDTO pageDTO){
        Long batchId = ((Batch) batchService.getCurBatch().getData()).getBatchId();
        if (clothService.getByBatchId(batchId, pageDTO) == null) {
            return JsonResponse.failure("当前不属于申请时间，无衣物展示");
        }
        Page<Cloth> clothPage = clothService.getByBatchId(batchId, pageDTO);
        JsonResponse jsonResponse = new JsonResponse();
        for (Cloth cloth : clothPage.getRecords()) {
            List<String> clothImgs = new ArrayList<>();
            for (ClothImg clothImg : clothImgService.getByClothId(cloth.getClothId())) {
                clothImgs.add(clothImg.getClothImg());
            }
            jsonResponse.addOtherData(String.valueOf(cloth.getClothId()), clothImgs);
        }
        jsonResponse.setStatus(true);
        jsonResponse.setData(clothPage);
        jsonResponse.setMessage("查询成功");
        return jsonResponse;
    }


    @RequestMapping(value = "/getClothByClothId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "根据衣服号查询衣服接口",notes = "应传入：clothId")
    public JsonResponse getClothByClothId(@RequestParam(value = "clothId") Long clothId){
        Cloth cloth = clothService.getByClothId(clothId);
        if (cloth == null) {
            return JsonResponse.failure("查询失败，不存在该衣服");
        }
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.addOtherData("clothImg", clothImgService.getByClothId(clothId));
        jsonResponse.setData(cloth);
        jsonResponse.setMessage("查询成功");
        jsonResponse.setStatus(true);
        return jsonResponse;
    }

    @RequestMapping(value = "/schoolGetClothByGender", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "根据性别查询衣服接口")
    public JsonResponse schoolGetClothByGender(@RequestBody @Valid Map<String, Object> map){
        PageDTO pageDTO = objectMapper.convertValue(map.get("pageList"), PageDTO.class);
        String gender = objectMapper.convertValue(map.get("gender"), String.class);
        return clothService.schoolGetClothByGender(gender, pageDTO);
    }
}
