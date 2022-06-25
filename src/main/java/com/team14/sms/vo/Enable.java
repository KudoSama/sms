package com.team14.sms.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wmj
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_enable")
@ApiModel(value="Enable对象", description="")
public class Enable extends Model<Enable> {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long stuId;


    @Override
    protected Serializable pkVal() {
        return this.stuId;
    }

}
