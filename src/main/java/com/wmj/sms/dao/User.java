package com.wmj.sms.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User {
    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String userType;

    private String password;

}
