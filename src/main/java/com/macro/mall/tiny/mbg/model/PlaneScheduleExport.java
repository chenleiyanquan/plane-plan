package com.macro.mall.tiny.mbg.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 数据库表：plane_schedule_export
 * @author Mybatis Generator
 */
public class PlaneScheduleExport implements Serializable {
    /**  */
    private Integer id;

    /**  */
    private String callsign;

    /**  */
    private String routing;

    /**  */
    private String registration;

    /** 出发时间 */
    private Date deptime;

    /** 到达时间 */
    private Date arrtime;

    /** 出发站 */
    private String origin;

    /** 到达站 */
    private String destination;

    /** 航空公司 */
    private String airline;

    /** 数据来源：0、原航班数据 1、生成的数据 */
    private Byte dataSource;

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return callsign 
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param callsign 
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     * @return routing 
     */
    public String getRouting() {
        return routing;
    }

    /**
     * @param routing 
     */
    public void setRouting(String routing) {
        this.routing = routing;
    }

    /**
     * @return registration 
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * @param registration 
     */
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    /**
     * @return deptime 出发时间
     */
    public Date getDeptime() {
        return deptime;
    }

    /**
     * @param deptime 出发时间
     */
    public void setDeptime(Date deptime) {
        this.deptime = deptime;
    }

    /**
     * @return arrtime 到达时间
     */
    public Date getArrtime() {
        return arrtime;
    }

    /**
     * @param arrtime 到达时间
     */
    public void setArrtime(Date arrtime) {
        this.arrtime = arrtime;
    }

    /**
     * @return origin 出发站
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin 出发站
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return destination 到达站
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination 到达站
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return airline 航空公司
     */
    public String getAirline() {
        return airline;
    }

    /**
     * @param airline 航空公司
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /**
     * @return data_source 数据来源：0、原航班数据 1、生成的数据
     */
    public Byte getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource 数据来源：0、原航班数据 1、生成的数据
     */
    public void setDataSource(Byte dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", callsign=").append(callsign);
        sb.append(", routing=").append(routing);
        sb.append(", registration=").append(registration);
        sb.append(", deptime=").append(deptime);
        sb.append(", arrtime=").append(arrtime);
        sb.append(", origin=").append(origin);
        sb.append(", destination=").append(destination);
        sb.append(", airline=").append(airline);
        sb.append(", dataSource=").append(dataSource);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}