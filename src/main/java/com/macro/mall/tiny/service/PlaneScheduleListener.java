package com.macro.mall.tiny.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.vo.PlaneScheduleExcelModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlaneScheduleListener extends AnalysisEventListener<PlaneScheduleExcelModel> {
    /**
     * 每隔5条存储数据库，实际使用中可以6000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 6000;
    List<PlaneScheduleExcelModel> list = new ArrayList<>();
    StringBuffer errorMessage = new StringBuffer();
    private PlaneService planeService;

    public PlaneScheduleListener(PlaneService planeService) {
        this.planeService = planeService;
    }
    public PlaneScheduleListener() {
    }

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }

    @Override
    public void invoke(PlaneScheduleExcelModel model, AnalysisContext analysisContext) {
        list.add(model);
        if (list.size() >= BATCH_COUNT) {
            clearAndsaveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    private void clearAndsaveData() {
        List<PlaneSchedule> planeScheduleList = list.stream().map(e->convert(e)).collect(Collectors.toList());
        planeService.batchInsert(planeScheduleList);
    }

    private PlaneSchedule convert(PlaneScheduleExcelModel e) {
        PlaneSchedule planeSchedule = new PlaneSchedule();
        BeanUtils.copyProperties(e,planeSchedule);
        planeSchedule.setDataSource((byte) 0);
        return planeSchedule;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return false;
    }

    @Override
    public void invokeHead(Map map, AnalysisContext analysisContext) {

    }
}
