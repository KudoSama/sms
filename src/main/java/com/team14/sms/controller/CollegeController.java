package com.team14.sms.controller;


import com.team14.sms.base.JsonResponse;
import com.team14.sms.mapper.CollegeMapper;
import com.team14.sms.service.CollegeService;
import com.team14.sms.vo.College;
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
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/college")
public class CollegeController extends BaseController {


    @Autowired
    private CollegeService collegeService;

    @Autowired
    private CollegeMapper collegeMapper;

    @RequestMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "添加学院接口",notes = "应传入：colId, colPassword")
    public JsonResponse addCollege(@RequestBody @Valid College college){
        try {
            collegeService.save(college);
            return JsonResponse.successMessage("添加成功");
        } catch (Exception e) {
            return JsonResponse.failure(e.toString());
        }
    }

}
