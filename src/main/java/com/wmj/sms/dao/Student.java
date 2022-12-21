package com.wmj.sms.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wmj
 * @since 2022-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wmj_t_student")
@ApiModel(value="Student对象", description="")
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long stuId;

    private String stuName;

    private String gender;

    private Date enDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long manId;

    private String stuPassword;

    private String userType;


    @Override
    public Serializable pkVal() {
        return this.stuId;
    }

}
