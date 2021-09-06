package com.macro.mall.tiny.mbg.mapper;

import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaneScheduleMapper {
    /**
     *  根据指定的条件获取数据库记录数
     * @param example
     */
    long countByExample(PlaneScheduleExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录
     * @param example
     */
    int deleteByExample(PlaneScheduleExample example);

    /**
     *  根据主键删除数据库的记录
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录
     * @param record
     */
    int insert(PlaneSchedule record);

    /**
     *  动态字段,写入数据库记录
     * @param record
     */
    int insertSelective(PlaneSchedule record);

    /**
     *  根据指定的条件查询符合条件的数据库记录
     * @param example
     */
    List<PlaneSchedule> selectByExample(PlaneScheduleExample example);

    /**
     *  根据指定主键获取一条数据库记录
     * @param id
     */
    PlaneSchedule selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PlaneSchedule record, @Param("example") PlaneScheduleExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PlaneSchedule record, @Param("example") PlaneScheduleExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录
     * @param record
     */
    int updateByPrimaryKeySelective(PlaneSchedule record);

    /**
     *  根据主键来更新符合条件的数据库记录
     * @param record
     */
    int updateByPrimaryKey(PlaneSchedule record);
}