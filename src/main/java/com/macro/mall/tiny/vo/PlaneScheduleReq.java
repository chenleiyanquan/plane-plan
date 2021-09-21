package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenlei
 * @Description: 每个时间点安排的航班总数
 * @Date: Created in 15:31 2019/5/14
 * @Modified By:
 */

@Data
public class PlaneScheduleReq {
    @ApiModelProperty("时间点（0~23点）")
    private List<PerHourPlaneLimitReq> hourPlanes;

    @ApiModelProperty("间隔时间(分钟),默认五分钟")
    private int intervalTime=5;
}
