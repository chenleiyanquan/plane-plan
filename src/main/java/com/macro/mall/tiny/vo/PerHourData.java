package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 15:31 2019/5/14
 * @Modified By:
 */
@Data
public class PerHourData {
    @ApiModelProperty("小时")
    private int perHour;

    @ApiModelProperty("到达航班数量")
    private int arriveCount;

    @ApiModelProperty("出发航班数量")
    private int departCount;

    @ApiModelProperty("剩余到达航班数量（距离到达航班数最大值）")
    private int remainArriveCount;

    @ApiModelProperty("剩余出发航班数量（距离出发航班数最大值）")
    private int remainDepartCount;

    @ApiModelProperty("到达增长比例")
    private double arrIncreRate;

    @ApiModelProperty("出发增长比例")
    private double depIncreRate;

    @ApiModelProperty("到达增长数")
    private int arrIncre;

    @ApiModelProperty("出发增长数")
    private int depIncre;
}
