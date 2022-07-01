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
 * @author wmj
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team14_t_manager")
@ApiModel(value="Manager对象", description="")
public class Manager extends Model<Manager> {

    private static final long serialVersionUID = 1L;

    private Long manId;

    private String manName;

    private String manPassword;

    private Long colId;

    private String userType;


    @Override
    public Serializable pkVal() {
        return this.manId;
    }

}
