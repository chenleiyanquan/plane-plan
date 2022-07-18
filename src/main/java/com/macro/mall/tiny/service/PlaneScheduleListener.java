package com.macro.mall.tiny.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.repository.PlaneRepository;
import com.macro.mall.tiny.vo.PlaneScheduleExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PlaneScheduleListener extends AnalysisEventListener<PlaneScheduleExcelModel> {
    private Map<Integer, String> headMap = new HashMap<>();

    private final PlaneRepository planeRepository;
    /**
     * 每隔5条存储数据库，实际使用中可以6000条，然后清理list ，方便内存回收
     */
  //  private static final int BATCH_COUNT = 90000;
    List<PlaneScheduleExcelModel> list = new ArrayList<>();

    public List<PlaneScheduleExcelModel> getList() {
        return list;
    }

    public void setList(List<PlaneScheduleExcelModel> list) {
        this.list = list;
    }

    public PlaneScheduleListener(PlaneRepository planeRepository){
        this.planeRepository = planeRepository;
    }

    /**
     * 解析头数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        this.headMap = headMap;
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
        log.info("解析到一条数据:{}", JSON.toJSONString(model));
        Integer rowIndex = analysisContext.readRowHolder().getRowIndex();
        log.info("解析到一条,第{}行,数据:{}", rowIndex, JSON.toJSONString(model));
        list.add(model);
    }

    private List<PlaneSchedule> convert(List<PlaneScheduleExcelModel> datas) {
        if(CollectionUtils.isEmpty(datas)){
            return new ArrayList<PlaneSchedule>() ;
        }
        return datas.stream().map(e->{
            System.out.println(JSON.toJSON(e));
            PlaneSchedule planeSchedule = new PlaneSchedule();
            BeanUtils.copyProperties(e,planeSchedule);
            planeSchedule.setAirtime(e.getTime());
            planeSchedule.setDataSource((byte) 0);
            planeSchedule.setAirhour(planeSchedule.getAirtime().getHours());
            return planeSchedule;
        }).collect(Collectors.toList());
    }

    private PlaneSchedule convert(PlaneScheduleExcelModel e) {
        PlaneSchedule planeSchedule = new PlaneSchedule();
        BeanUtils.copyProperties(e,planeSchedule);
        planeSchedule.setDataSource((byte) 0);
        return planeSchedule;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        planeRepository.batchInsert(convert(list));
    }

}
