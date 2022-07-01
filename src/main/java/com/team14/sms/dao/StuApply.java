package com.team14.sms.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

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
 * @since 2022-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_stu_apply")
@ApiModel(value="StuApply对象", description="")
public class StuApply extends Model<StuApply> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long stuId;

    private Long batchId;

    private String appReason;

    private Long manId;

    private Long colId;

    private Date scDate;

    private Long clothId;

    private String clothSize;

    private Long state;

    private String refReason;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
