package com.macro.mall.tiny.service;


import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExport;
import com.macro.mall.tiny.vo.IncrementVo;
import com.macro.mall.tiny.vo.PerHourPlane;
import com.macro.mall.tiny.vo.PerHourPlaneCount;
import com.macro.mall.tiny.vo.PlaneScheduleExcel;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 9:45 2019/5/14
 * @Modified By:
 */
public interface PlaneService {
    long getTotalPlaneCount(int i);

    List<PerHourPlane> getPerHourArrivePlanes();

    List<PerHourPlane> getPerHourDepartPlanes();

    long getAirPlaneConut(int i);

    //查询原安排表机场每小时到达航班数
    List<PerHourPlaneCount> getPerHourArrivePlanesCount();

    //查询原安排表机场每小时起飞航班数
    List<PerHourPlaneCount> getPerHourDepartPlanesCount();

    long addPlaneExport(PlaneScheduleExport export);

    void transcateExport();
    //0 所有 1、起飞 2 到达
    List<PlaneSchedule> getAllPlanesByType(int i);

    /**
     * 查询每小时到达的航班
     * @param perHour
     * @return
     */
    List<PlaneSchedule> getPlanesByArriveHour(int perHour) throws ParseException;

    long savePlane(PlaneSchedule newPlane);

    PlaneSchedule getPlaneScheduleImage(PlaneSchedule planeSchedule);

    List<IncrementVo> getIncrementedCount();

    List<PlaneSchedule> getPlanesByDepHour(int perHour) throws ParseException;

    List<PlaneScheduleExcel> getPlanesExcelVos();

    void importInfo(MultipartFile file) throws Exception;
}
