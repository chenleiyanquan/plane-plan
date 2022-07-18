package com.macro.mall.tiny.mbg.mapper;

import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaneScheduleMapper {
    long countByExample(PlaneScheduleExample example);

    int deleteByExample(PlaneScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlaneSchedule record);

    int insertSelective(PlaneSchedule record);

    List<PlaneSchedule> selectByExample(PlaneScheduleExample example);

    PlaneSchedule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlaneSchedule record, @Param("example") PlaneScheduleExample example);

    int updateByExample(@Param("record") PlaneSchedule record, @Param("example") PlaneScheduleExample example);

    int updateByPrimaryKeySelective(PlaneSchedule record);

    int updateByPrimaryKey(PlaneSchedule record);
}