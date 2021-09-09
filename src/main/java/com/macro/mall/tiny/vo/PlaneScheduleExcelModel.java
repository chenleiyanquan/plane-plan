package com.macro.mall.tiny.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PlaneScheduleExcelModel {
    @ExcelProperty(value = "callsign")
    //@NotBlank(message="callsign不能为空")
    private String callsign;

    @ExcelProperty(value = "routing")
    //@NotBlank(message="routing不能为空")
    private String routing;

    @ExcelProperty(value = "registration")
    //@NotBlank(message="月份不能为空")
    private String registration;

    /** 出发时间 */
    @ExcelProperty(value = "deptime")
   //@NotBlank(message="deptime不能为空")
    @DateTimeFormat("HH:mm:ss")
    private Date deptime;

    /** 到达时间 */
    @ExcelProperty(value = "arrtime")
    //@NotBlank(message="arrtime不能为空")
    @DateTimeFormat("HH:mm:ss")
    private Date arrtime;

    /** 出发站 */
    @ExcelProperty(value = "origin")
   // @NotBlank(message="origin不能为空")
    private String origin;

    /** 到达站 */
    @ExcelProperty(value = "destination")
    //@NotBlank(message="destination不能为空")
    private String destination;

    /** 航空公司 */
    @ExcelProperty(value = "airline")
    //@NotBlank(message="airline不能为空")
    private String airline;

    /** 数据来源：0、原航班数据 1、生成的数据 */
//    private Byte dataSource=0;
}