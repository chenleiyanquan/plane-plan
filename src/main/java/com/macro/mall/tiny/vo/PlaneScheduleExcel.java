package com.macro.mall.tiny.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

/**
 * 
 * 数据库表：plane_schedule
 * @author Mybatis Generator
 */
@Data
public class PlaneScheduleExcel extends BaseRowModel {
//    /**  */
//    @ExcelProperty(value = "中台商户id", index = 0)
//    private Integer id;

    @ExcelProperty(value = "callsign", index = 0)
    private String callsign;

    @ExcelProperty(value = "routing", index = 1)
    private String routing;

    @ExcelProperty(value = "registration", index = 2)
    private String registration;

    @ExcelProperty(value = "deptime", format ="HH:mm:ss",index = 3)
    private Date deptime;

    @ExcelProperty(value = "arrtime",format ="HH:mm:ss", index = 4)
    private Date arrtime;

    @ExcelProperty(value = "origin", index = 5)
    private String origin;

    @ExcelProperty(value = "destination", index = 6)
    private String destination;

    /** 航空公司 */
    @ExcelProperty(value = "airline", index = 7)
    private String airline;

//    /** 数据来源：0、原航班数据 1、生成的数据 */
//    @ExcelProperty(value = "中台商户id", index = 0)
//    private Byte dataSource;
}