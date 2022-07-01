package com.team14.sms.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
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
@TableName("team14_t_cloth_size")
@ApiModel(value="ClothSize对象", description="")
public class ClothSize extends Model<ClothSize> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long clothId;

    private String clothSize;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
