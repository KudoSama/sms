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
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_school")
@ApiModel(value="School对象", description="")
public class School extends Model<School> {

    private static final long serialVersionUID = 1L;

    private Long schId;

    private String schName;

    private String schPassword;

    private String userType;


    @Override
    public Serializable pkVal() {
        return this.schId;
    }

}
