package com.macro.mall.tiny.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
    @ColumnWidth(20)
    private String callsign;

    @ExcelProperty(value = "registration", index = 1)
    @ColumnWidth(20)
    private String registration;

    @ExcelProperty(value = "origin", index = 2)
    @ColumnWidth(20)
    private String origin;

    @ExcelProperty(value = "destination", index = 3)
    @ColumnWidth(20)
    private String destination;

    /** 航空公司 */
    @ExcelProperty(value = "airline", index = 4)
    @ColumnWidth(20)
    private String airline;

    @ExcelProperty(value = "airtype", index = 5)
    @ColumnWidth(20)
    private String airtype;
    /**
     * 航班时间
     */
    @ExcelProperty(value = "airtime", index = 6)
    @ColumnWidth(20)
    @DateTimeFormat("HH:mm:ss")
    private Date airtime;

//    @ExcelProperty(value = "dataSource(0、原航班数据 1、生成的数据)", index = 7)
//    private Byte dataSource;


//    /** 数据来源：0、原航班数据 1、生成的数据 */
//    @ExcelProperty(value = "中台商户id", index = 0)
//    private Byte dataSource;
}