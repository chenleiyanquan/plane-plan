package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 9:49 2019/5/14
 * @Modified By:
 */
@Data
public class PlaneReq {
    @ApiModelProperty("年增量")
    private double incrementRate;

    @ApiModelProperty("手动调整系数,默认为：0")
    private double manualRate;

    @ApiModelProperty("高峰时的上限")
    private int max;

    @ApiModelProperty("指定公司增量占比")
    private List<IncrementDetail> incrementDetails;


}
