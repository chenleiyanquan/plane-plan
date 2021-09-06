package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 航空公司航班增量
 * @Author: chenlei
 * @Description:
 * @Date: Created in 11:12 2019/5/14
 * @Modified By:
 */
@Data
public class PlaneIncrement {

    @ApiModelProperty("航空公司")
    private String airline;

    @ApiModelProperty("航班数量")
    private int planeCount;

    @ApiModelProperty("航班数量")
    private int incrementCount;
}
