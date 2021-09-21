package com.macro.mall.tiny.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class PlaneScheduleExcelModel{
   // @ExcelProperty(value = "交易编号")
    //@NotBlank(message="callsign不能为空")
    private String jiaoyiNo;

    //@ExcelProperty(value = "callsign")
    //@NotBlank(message="callsign不能为空")
    private String callsign;

    //@ExcelProperty(value = "registration")
    //@NotBlank(message="月份不能为空")
    private String registration;

   // @ExcelProperty(value = "airTime")
   // @DateTimeFormat("HH:mm:ss")
   // @NotBlank(message="航班时间不能为空")
    private String airTime;

    /** 出发站 */
   // @ExcelProperty(value = "origin")
   // @NotBlank(message="origin不能为空")
    private String origin;

    /** 到达站 */
   // @ExcelProperty(value = "destination")
    //@NotBlank(message="destination不能为空")
    private String destination;

    /** 航空公司 */
   // @ExcelProperty(value = "airline")
    //@NotBlank(message="airline不能为空")
    private String airline;

   // @ExcelProperty(value = "airType")
    //@NotBlank(message="airline不能为空")
    private String airType;
}
