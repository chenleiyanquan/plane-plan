package com.macro.mall.tiny.mbg.mapper;

import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlaneScheduleMapper {
    long countByExample(PlaneScheduleExample example);

    int deleteByExample(PlaneScheduleExample example);

    List<PlaneSchedule> selectByExample(PlaneScheduleExample example);

    int updateByExampleSelective(@Param("record") PlaneSchedule record, @Param("example") PlaneScheduleExample example);

    int updateByExample(@Param("record") PlaneSchedule record, @Param("example") PlaneScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlaneSchedule record);

    int insertSelective(PlaneSchedule record);

    PlaneSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlaneSchedule record);

    int updateByPrimaryKey(PlaneSchedule record);
}