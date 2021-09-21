package com.macro.mall.tiny.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;

public class PlaneScheduleExcelModel{
    @ExcelProperty(value = "交易编号")
    //@NotBlank(message="callsign不能为空")
    private String jiaoyiNo;

    @ExcelProperty(value = "callsign")
    //@NotBlank(message="callsign不能为空")
    private String callsign;

    @ExcelProperty(value = "registration")
    //@NotBlank(message="月份不能为空")
    private String registration;

    @ExcelProperty(value = "airTime")
    @DateTimeFormat("HH:mm:ss")
    @NotBlank(message="航班时间不能为空")
    private String airTime;

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

    @ExcelProperty(value = "airType")
    //@NotBlank(message="airline不能为空")
    private String airType;

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getAirTime() {
        return airTime;
    }

    public void setAirTime(String airTime) {
        this.airTime = airTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirType() {
        return airType;
    }

    public void setAirType(String airType) {
        this.airType = airType;
    }

/** 数据来源：0、原航班数据 1、生成的数据 */
//    private Byte dataSource=0;

}
