package com.macro.mall.tiny.mbg.repository;

import com.macro.mall.tiny.mbg.mapper.PlaneScheduleExtMapper;
import com.macro.mall.tiny.mbg.mapper.PlaneScheduleMapper;
import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExample;
import com.macro.mall.tiny.vo.IncrementVo;
import com.macro.mall.tiny.vo.PerHourPlane;
import com.macro.mall.tiny.vo.PerHourPlaneCount;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 9:56 2019/5/14
 * @Modified By:
 */
@Repository
public class PlaneRepository {
    @Resource
    private PlaneScheduleMapper planeScheduleMapper;

    @Resource
    private PlaneScheduleExtMapper planeScheduleExtMapper;

    public List<PlaneSchedule> getPlaneScheduleList(String airLine){
        PlaneScheduleExample example = new PlaneScheduleExample();
        PlaneScheduleExample.Criteria criteria = example.createCriteria();
        criteria.andAirlineEqualTo(airLine);
        return planeScheduleMapper.selectByExample(example);
    }

    public long getTotalPlaneCount(int source) {
        PlaneScheduleExample example = new PlaneScheduleExample();
        PlaneScheduleExample.Criteria criteria = example.createCriteria();
        criteria.andDataSourceEqualTo((byte)source);
        return planeScheduleMapper.countByExample(example);
    }

    public List<PerHourPlane> getPerHourArrivePlanes(){
        return planeScheduleExtMapper.getPerHourArrivePlanes();
    };

    public List<PerHourPlane> getPerHourDepartPlanes(){
        return planeScheduleExtMapper.getPerHourDepartPlanes();
    };

    public long getAirPlaneConut(int source) {
        return planeScheduleExtMapper.getAirPlaneConut(source);
    }

    //查询原安排表机场每小时到达航班数
    public List<PerHourPlaneCount> getPerHourArrivePlanesCount(){
        return planeScheduleExtMapper.getPerHourArrivePlanesCount();
    };
    //查询原安排表机场每小时起飞航班数
    public List<PerHourPlaneCount> getPerHourDepartPlanesCount(){
        return planeScheduleExtMapper.getPerHourDepartPlanesCount();
    };

    public List<PlaneSchedule> getAllPlanesByType(int i) {
        PlaneScheduleExample example = new PlaneScheduleExample();
        PlaneScheduleExample.Criteria criteria = example.createCriteria();
        if(i==1){
            //起飞
            criteria.andDeptimeIsNotNull();
        }
        if(i==2){
            //到达
            criteria.andArrtimeIsNotNull();
        }

        return planeScheduleMapper.selectByExample(example);
    }

    public List<PlaneSchedule> getPlanesByArriveHour(int perHour) throws ParseException {
        PlaneScheduleExample example = new PlaneScheduleExample();
        PlaneScheduleExample.Criteria criteria = example.createCriteria();
        if(perHour!=23){
            criteria.andArrtimeBetween(DateUtils.parseDate(perHour+":00:00","HH:mm:ss"),DateUtils.parseDate(perHour+":59:59","HH:mm:ss"));
        }else{
            criteria.andArrtimeGreaterThanOrEqualTo(DateUtils.parseDate("23:00:00","HH:mm:ss"));
        }
        return  planeScheduleMapper.selectByExample(example);
    }

    public long savePlane(PlaneSchedule newPlane) {
        return  planeScheduleMapper.insertSelective(newPlane);
    }

    public PlaneSchedule getPlaneScheduleImage(String airline, String registration, Date arrtime) {
       return planeScheduleExtMapper.getPlaneScheduleImage(airline,registration,arrtime);
    }

    public List<IncrementVo> getIncrementedCount() {
        return planeScheduleExtMapper.getIncrementedCount();
    }

    public List<PlaneSchedule> getPlanesByDepHour(int perHour) throws ParseException {
        PlaneScheduleExample example = new PlaneScheduleExample();
        PlaneScheduleExample.Criteria criteria = example.createCriteria();
        if(perHour!=23){
            criteria.andDeptimeBetween(DateUtils.parseDate(perHour+":00:00","HH:mm:ss"),DateUtils.parseDate(perHour+":59:59","HH:mm:ss"));
        }else{
            criteria.andDeptimeGreaterThanOrEqualTo(DateUtils.parseDate("23:00:00","HH:mm:ss"));
        }
        return  planeScheduleMapper.selectByExample(example);
    }

    public List<PlaneSchedule> getAllPlanesByTime() {
       return planeScheduleExtMapper.getAllPlanesByTime();
    }
}
