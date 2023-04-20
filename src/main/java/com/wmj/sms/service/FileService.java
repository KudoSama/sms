package com.wmj.sms.service;


import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface FileService {

    Map upload(MultipartFile file) throws IOException;

    void download(HttpServletResponse response);

    void delete(String filePath);
}
