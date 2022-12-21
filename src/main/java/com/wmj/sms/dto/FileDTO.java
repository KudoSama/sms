package com.wmj.sms.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileDTO extends Model<FileDTO> {
    private Long clothId;
    private MultipartFile file;
}
