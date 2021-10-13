package com.macro.mall.tiny.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class PlaneScheduleExcelModel extends BaseRowModel {

    @ExcelProperty(value = "callsign")
    @NotBlank(message="callsign不能为空")
    private String callsign;

    @ExcelProperty(value = "registration")
    @NotBlank(message="registration不能为空")
    private String registration;

    @ExcelProperty(value = "time")
    @DateTimeFormat("HH:mm:ss")
    @NotBlank(message="time不能为空")
    private Date time;

    /** 出发站 */
    @ExcelProperty(value = "origin")
    @NotBlank(message="origin不能为空")
    private String origin;

    /** 到达站 */
    @ExcelProperty(value = "destination")
    @NotBlank(message="destination不能为空")
    private String destination;

    /** 航空公司 */
    @ExcelProperty(value = "airline")
    @NotBlank(message="airline不能为空")
    private String airline;

    @ExcelProperty(value = "airType")
    @NotBlank(message="airline不能为空")
    private String airType;
}
