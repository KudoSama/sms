package com.wmj.sms.mapper;

import com.wmj.sms.dao.ClothImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmj
 * @since 2022-12-27
 */
@Mapper
@Component
public interface ClothImgMapper extends BaseMapper<ClothImg> {

    void deleteByClothId(Long clothId);

    void deleteByClothImg(String clothImg);
}
