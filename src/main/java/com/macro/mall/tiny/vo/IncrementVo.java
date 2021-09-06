package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IncrementVo {
    @ApiModelProperty("小时")
    private int perHour;

    @ApiModelProperty("到达航班已增增量")
    private int arriveIncremented;

    @ApiModelProperty("出发航班已增增量数量")
    private int departIncremented;
}
