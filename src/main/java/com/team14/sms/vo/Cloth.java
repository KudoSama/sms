package com.team14.sms.vo;

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
 * @author wmj
 * @since 2022-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_cloth")
@ApiModel(value="Cloth对象", description="")
public class Cloth extends Model<Cloth> {

    private static final long serialVersionUID = 1L;

    private Long clothId;

    private String clothName;

    private String gender;

    private Long batchId;


    @Override
    public Serializable pkVal() {
        return this.clothId;
    }

}
