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
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_college")
@ApiModel(value="College对象", description="")
public class College extends Model<College> {

    private static final long serialVersionUID = 1L;

    private Long colId;

    private String colName;

    private String colPassword;

    private String userType;


    @Override
    public Serializable pkVal() {
        return this.colId;
    }

}
