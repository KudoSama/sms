package com.team14.sms.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_batch")
@ApiModel(value="Batch对象", description="")
public class Batch extends Model<Batch> {

    private static final long serialVersionUID = 1L;

    private Long batchId;

    @TableField("batch_dateStart")
    private Date batchDatestart;

    @TableField("batch_dateEnd")
    private Date batchDateend;


    @Override
    public Serializable pkVal() {
        return this.batchId;
    }

}
