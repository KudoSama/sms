package com.wmj.sms.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CountResultByStudent extends Model<CountResultByStudent> {
    @TableId(value = "stu_id",type = IdType.AUTO)

    @JsonSerialize(using = ToStringSerializer.class)
    private Long stuId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long clothId;

    private String clothSize;

    private int num;
}
