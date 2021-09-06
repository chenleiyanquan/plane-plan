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
public class AirLineHourData {
    @ApiModelProperty("小时")
    private int perHour;

   // @ApiModelProperty("航空公司")
   // private String airline;

    @ApiModelProperty("到达航班数量")
    private int arriveCount;

    @ApiModelProperty("出发航班数量")
    private int departCount;

  //  @ApiModelProperty("到达航班增量比例")
  //  private double arriveIncrementRate;

  //  @ApiModelProperty("出发航班数量比例")
   // private double departIncrementRate;

    @ApiModelProperty("到达航班增量")
    private int arriveIncrement;

    @ApiModelProperty("出发航班增量")
    private int departIncrement;

    @ApiModelProperty("到达航班已增增量")
    private int arriveIncremented;

    @ApiModelProperty("出发航班已增增量")
    private int departIncremented;

    @ApiModelProperty("出发航班剩余增量")
    private int departRemainIncremented;
}
