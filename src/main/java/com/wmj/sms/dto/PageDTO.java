package com.wmj.sms.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageDTO extends Model <PageDTO>{
    private Integer pageNo = 1;
    private Integer pageSize = 10;
}
