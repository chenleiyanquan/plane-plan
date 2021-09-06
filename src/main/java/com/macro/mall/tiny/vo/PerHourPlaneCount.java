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
public class PerHourPlaneCount {
    @ApiModelProperty("小时")
    private int perHour;

    @ApiModelProperty("航班状态：arr 到达，dep起飞")
    private String rounting;

    @ApiModelProperty("航班数量")
    private int planeCount;
}
