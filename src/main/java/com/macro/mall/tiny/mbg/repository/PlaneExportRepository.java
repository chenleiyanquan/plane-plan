package com.macro.mall.tiny.mbg.repository;

import com.macro.mall.tiny.mbg.mapper.PlaneScheduleExportMapper;
import com.macro.mall.tiny.mbg.mapper.PlaneScheduleExtMapper;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 9:56 2019/5/14
 * @Modified By:
 */
@Repository
public class PlaneExportRepository {
    @Resource
    private PlaneScheduleExportMapper planeScheduleExportMapper;
    @Resource
    private PlaneScheduleExtMapper planeScheduleExtMapper;

   public int insertSeletive(PlaneScheduleExport export){
       return planeScheduleExportMapper.insertSelective(export);
   }
    public void transcateExport() {
        planeScheduleExtMapper.transcateExport();
    }
}
