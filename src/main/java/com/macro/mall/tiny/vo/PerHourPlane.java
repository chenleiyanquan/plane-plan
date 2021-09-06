package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 11:12 2019/5/14
 * @Modified By:
 */
@Data
public class PerHourPlane {
    @ApiModelProperty("小时")
    private int perHour;

    @ApiModelProperty("航空公司")
    private String airline;

    @ApiModelProperty("航班数量")
    private int planeCount;
}
