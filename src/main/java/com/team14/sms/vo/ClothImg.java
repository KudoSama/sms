package com.team14.sms.vo;

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
 * @author lmh
 * @since 2022-06-27
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_cloth_img")
@ApiModel(value="ClothImg对象", description="")
public class ClothImg extends Model<ClothImg> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long clothId;

    private String clothImg;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
