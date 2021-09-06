package com.macro.mall.tiny.mbg.mapper;

import com.macro.mall.tiny.mbg.model.PlaneScheduleExport;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExportExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaneScheduleExportMapper {
    /**
     *  根据指定的条件获取数据库记录数
     * @param example
     */
    long countByExample(PlaneScheduleExportExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录
     * @param example
     */
    int deleteByExample(PlaneScheduleExportExample example);

    /**
     *  根据主键删除数据库的记录
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录
     * @param record
     */
    int insert(PlaneScheduleExport record);

    /**
     *  动态字段,写入数据库记录
     * @param record
     */
    int insertSelective(PlaneScheduleExport record);

    /**
     *  根据指定的条件查询符合条件的数据库记录
     * @param example
     */
    List<PlaneScheduleExport> selectByExample(PlaneScheduleExportExample example);

    /**
     *  根据指定主键获取一条数据库记录
     * @param id
     */
    PlaneScheduleExport selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PlaneScheduleExport record, @Param("example") PlaneScheduleExportExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PlaneScheduleExport record, @Param("example") PlaneScheduleExportExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录
     * @param record
     */
    int updateByPrimaryKeySelective(PlaneScheduleExport record);

    /**
     *  根据主键来更新符合条件的数据库记录
     * @param record
     */
    int updateByPrimaryKey(PlaneScheduleExport record);
}