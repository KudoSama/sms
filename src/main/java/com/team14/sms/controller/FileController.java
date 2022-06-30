package com.team14.sms.controller;


import com.team14.sms.service.FileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/file")
@Slf4j
public class FileController {
    protected FileService fileService;

    protected ResourceLoader resourceLoader;

    public FileController(FileService fileService, ResourceLoader resourceLoader) {
        this.fileService = fileService;
        this.resourceLoader = resourceLoader;
    }

    @ApiOperation(value = "文件上传", notes = "文件上传，传入文件、clothId")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParam(name = "clothId", value = "衣服商品号", required = true, dataType = "Long",dataTypeClass = Long.class)
    public ResponseEntity<Map<String, String>> upload(Long clothId, MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> map = new HashMap();
        map = fileService.upload(clothId, file);
        return ResponseEntity.ok().body(map);
    }

    private static String suffix(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i == -1 ? "" : fileName.substring(i + 1);
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response){
        fileService.download(response);
    }
}
