package com.team14.sms.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User {
    @TableId("id")
    private Long id;

    private String name;

    private String userType;

    private String password;

}
