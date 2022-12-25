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
public class CountResult extends Model<CountResult> {
    @TableId(value = "col_id",type = IdType.AUTO)

    @JsonSerialize(using = ToStringSerializer.class)
    private Long colId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long clothId;

    private String clothSize;

    private int num;
}
