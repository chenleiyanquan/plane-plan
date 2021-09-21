package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: chenlei
 * @Description: 每个时间点安排的航班总数
 * @Date: Created in 15:31 2019/5/14
 * @Modified By:
 */
@Data
public class PerHourPlaneLimitReq {
    @ApiModelProperty("时间点（0~23点）")
    private int perHour;

    @ApiModelProperty("安排的航班数量")
    private int planeCount;
}
