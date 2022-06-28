package com.team14.sms.service;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface FileService {

    Map upload(Long clothId, MultipartFile file) throws IOException;

    void download(HttpServletResponse response);
}
