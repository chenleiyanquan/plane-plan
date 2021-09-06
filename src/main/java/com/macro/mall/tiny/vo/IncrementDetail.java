package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 10:17 2019/5/14
 * @Modified By:
 */
@Data
public class IncrementDetail {
    @ApiModelProperty("航空公司名")
    private String companyName;

    @ApiModelProperty("增长占比")
    private double incrementRate;
}
