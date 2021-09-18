package com.macro.mall.tiny.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.vo.PlaneScheduleExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PlaneScheduleListener extends AnalysisEventListener<PlaneScheduleExcelModel> {
    final static Logger logger = LoggerFactory.getLogger(PlaneScheduleListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以6000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 6000;
    List<PlaneScheduleExcelModel> list = new ArrayList<>();
    private PlaneService planeService;

    public PlaneScheduleListener(PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public void onException(Exception exception, AnalysisContext analysisContext) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常：{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), exception.getMessage());
            throw new Exception("第"+excelDataConvertException.getRowIndex()+"行，第"+excelDataConvertException.getColumnIndex()+"列解析异常："+exception.getMessage());
        } else if (exception instanceof RuntimeException) {
            throw exception;
        }
    }

    @Override
    public void invoke(PlaneScheduleExcelModel model, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(model));
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
