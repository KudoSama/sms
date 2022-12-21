package com.wmj.sms.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wmj_t_stu_apply")
@ApiModel(value="StuApply对象", description="")
public class StuApply extends Model<StuApply> {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long stuId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long batchId;

    private String appReason;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long manId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long colId;

    private Date scDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long clothId;

    private String clothSize;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long state;

    private String refReason;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
