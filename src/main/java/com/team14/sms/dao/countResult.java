package com.team14.sms.dao;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class countResult extends Model<countResult> {
    private Long colId;

    private Long clothId;

    private String clothSize;

    private int num;
}
