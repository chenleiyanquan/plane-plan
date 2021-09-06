package com.macro.mall.tiny.mbg.mapper;

import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.vo.IncrementVo;
import com.macro.mall.tiny.vo.OrderlyPlane;
import com.macro.mall.tiny.vo.PerHourPlane;
import com.macro.mall.tiny.vo.PerHourPlaneCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PlaneScheduleExtMapper {
    //查询原安排表机场每小时每个航空公司的到达航班数
    List<PerHourPlane> getPerHourArrivePlanes();
    //查询原安排表机场每小时每个航空公司的起飞航班数
    List<PerHourPlane> getPerHourDepartPlanes();

    //查询原安排表机场每小时到达航班数
    List<PerHourPlaneCount> getPerHourArrivePlanesCount();
    //查询原安排表机场每小时起飞航班数
    List<PerHourPlaneCount> getPerHourDepartPlanesCount();
    //查询航空公司数
    long getAirPlaneConut(int source);

    void transcateExport();

    List<OrderlyPlane> getAllPlanesOrderly();

    PlaneSchedule getPlaneScheduleImage(@Param("airline")String airline, @Param("registration")String registration, @Param("arrtime")Date arrtime);

    List<IncrementVo> getIncrementedCount();

    List<PlaneSchedule> getAllPlanesByTime();
}