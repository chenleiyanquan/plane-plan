package com.macro.mall.tiny.mbg.model;

import java.util.Date;
import lombok.Data;

@Data
public class PlaneSchedule {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String callsign;

    private String registration;

    /**
     * 出发站
     */
    private String origin;

    /**
     * 到达站
     */
    private String destination;

    /**
     * 航空公司
     */
    private String airline;

    /**
     * 数据来源：0、原航班数据 1、生成的数据
     */
    private Byte dataSource;

    private String airtype;

    /**
     * 航班时间
     */
    private Date airtime;

    /**
     * 出发时间
     */
    private Date deptime;

    private String routing;

    /**
     * 到达时间
     */
    private Date arrtime;

    /**
     * 航班时间整点
     */
    private Integer airhour;
}