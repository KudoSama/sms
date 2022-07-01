package com.team14.sms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team14.sms.base.JsonResponse;
import com.team14.sms.dao.ClothImg;
import com.team14.sms.dto.PageDTO;
import com.team14.sms.mapper.ClothMapper;
import com.team14.sms.service.ClothImgService;
import com.team14.sms.service.ClothService;
import com.team14.sms.dao.Cloth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.team14.sms.base.BaseController;

import javax.validation.Valid;
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

    @Autowired
    private ClothImgService clothImgService;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加服装接口",notes = "应传入：clothId,clothName,gender,batchId")
    public JsonResponse addCloth(@RequestBody @Valid Cloth cloth){
        return clothService.addState(cloth);
    }

    @RequestMapping(value = "/getClothByGender", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装性别接口",notes = "应传入gender：男、女（字符串）, pageNo, pageSize")
    public JsonResponse getClothByGender(@RequestParam(value = "gender") String gender,
                                         @RequestParam(value = "pageNo") Integer pageNo,
                                         @RequestParam(value = "pageSize") Integer pageSize){
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageNo(pageNo);
        pageDTO.setPageSize(pageSize);
        // System.out.println(pageDTO.getPageNo() + pageDTO.getPageSize());
        if (clothService.getByGender(gender, pageDTO) == null) {
            return JsonResponse.failure("当前不属于审批时间，请等待学生申请结束");
        }
        Page<Cloth> clothPage = clothService.getByGender(gender, pageDTO);
        JsonResponse jsonResponse = new JsonResponse();
        for (Cloth cloth : clothPage.getRecords()) {
            jsonResponse.addOtherData(String.valueOf(cloth.getClothId()), clothImgService.getByClothId(cloth.getClothId()));
        }
        jsonResponse.setData(clothPage);
        jsonResponse.setMessage("查询成功");
        return jsonResponse;
    }

    @RequestMapping(value = "/getClothByBatchId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "服装批次接口",notes = "应传入：batchId, pageNo, pageSize")
    public JsonResponse getClothByBatchId(@RequestParam(value = "batchId") Long batchId,
                                          @RequestParam(value = "pageNo") Integer pageNo,
                                          @RequestParam(value = "pageSize") Integer pageSize){
        PageDTO pageDTO = new PageDTO();
        // System.out.println(pageNo + pageSize);
        pageDTO.setPageNo(pageNo);
        pageDTO.setPageSize(pageSize);
        if (clothService.getByBatchId(batchId, pageDTO) == null) {
            return JsonResponse.failure("当前不属于审批时间，请等待学生申请结束");
        }
        Page<Cloth> clothPage = clothService.getByBatchId(batchId, pageDTO);
        JsonResponse jsonResponse = new JsonResponse();
        for (Cloth cloth : clothPage.getRecords()) {
            jsonResponse.addOtherData(String.valueOf(cloth.getClothId()), clothImgService.getByClothId(cloth.getClothId()));
        }
        jsonResponse.setData(clothPage);
        jsonResponse.setMessage("查询成功");
        return jsonResponse;
    }

    @RequestMapping(value = "/getClothByClothId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "根据衣服号查询衣服接口",notes = "应传入：clothId")

    // @ApiImplicitParam(name = "clothId", value = "衣服号", required = true, dataType = "Long",dataTypeClass = Long.class)
    public JsonResponse getClothByClothId(@RequestParam(value = "clothId") Long clothId){
        Cloth cloth = clothService.getByClothId(clothId);
        if (cloth == null) {
            return JsonResponse.failure("查询失败，不存在该衣服");
        }
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.addOtherData("clothImg", clothImgService.getByClothId(clothId));
        jsonResponse.setData(cloth);
        jsonResponse.setMessage("查询成功");
        return jsonResponse;
    }
}
