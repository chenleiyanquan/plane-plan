package com.macro.mall.tiny.vo;

import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 21:46 2019/8/6
 * @Modified By:
 */
@Data
public class OrderlyPlane extends PlaneSchedule {

    @ApiModelProperty("小时")
    private int perHour;
}
