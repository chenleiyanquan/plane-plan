package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.model.PlaneScheduleExport;
import com.macro.mall.tiny.mbg.repository.PlaneExportRepository;
import com.macro.mall.tiny.mbg.repository.PlaneRepository;
import com.macro.mall.tiny.service.PlaneService;
import com.macro.mall.tiny.vo.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenlei
 * @Description:
 * @Date: Created in 9:53 2019/5/14
 * @Modified By:
 */
@Service("planeService")
public class PlaneServiceImpl implements PlaneService {
    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private PlaneExportRepository planeExportRepository;

    @Override
    public long getTotalPlaneCount(int source) {
        return planeRepository.getTotalPlaneCount(source);
    }

    @Override
    public List<PerHourPlane> getPerHourArrivePlanes(){
        return planeRepository.getPerHourArrivePlanes();
    };

    @Override
    public List<PerHourPlane> getPerHourDepartPlanes(){
        return planeRepository.getPerHourDepartPlanes();
    };

    @Override
    public long getAirPlaneConut(int source) {
        return planeRepository.getAirPlaneConut(source);
    }

    //查询原安排表机场每小时到达航班数
    @Override
    public List<PerHourPlaneCount> getPerHourArrivePlanesCount(){
        return planeRepository.getPerHourArrivePlanesCount();
    };
    //查询原安排表机场每小时起飞航班数
    @Override
    public List<PerHourPlaneCount> getPerHourDepartPlanesCount(){
        return planeRepository.getPerHourDepartPlanesCount();
    };

    @Override
    public long addPlaneExport(PlaneScheduleExport export){
       return planeExportRepository.insertSeletive(export);
    }

    @Override
    public void transcateExport() {
        planeExportRepository.transcateExport();
    }

    @Override
    public List<PlaneSchedule> getAllPlanesByType(int i) {
       return planeRepository.getAllPlanesByType(i);
    }

    @Override
    public List<PlaneSchedule> getPlanesByArriveHour(int perHour) throws ParseException {
        return planeRepository.getPlanesByArriveHour(perHour);
    }

    @Override
    public long savePlane(PlaneSchedule newPlane) {
        return planeRepository.savePlane(newPlane);
    }

    @Override
    public PlaneSchedule getPlaneScheduleImage(PlaneSchedule planeSchedule) {
        return planeRepository.getPlaneScheduleImage(planeSchedule.getAirline(),planeSchedule.getRegistration(),planeSchedule.getArrtime());
    }

    @Override
    public List<IncrementVo> getIncrementedCount() {
        return planeRepository.getIncrementedCount();
    }

    @Override
    public List<PlaneSchedule> getPlanesByDepHour(int perHour) throws ParseException {
        return planeRepository.getPlanesByDepHour(perHour);
    }

    @Override
    public List<PlaneScheduleExcel> getPlanesExcelVos() {
        List<PlaneSchedule> list =  planeRepository.getAllPlanesByTime();
        List<PlaneScheduleExcel> result = new ArrayList<>();
        list.stream().forEach(planeSchedule -> {
            PlaneScheduleExcel excel = new PlaneScheduleExcel();
            BeanUtils.copyProperties(planeSchedule,excel);
            result.add(excel);
        });
        return result;
    }

    public void importInfo(MultipartFile file) throws Exception {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new Exception("参数不能为空");
        }
        Workbook workbook = null;
        try {
            InputStream inputStream = file.getInputStream();
            if (file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("XLSX")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                throw new Exception("文件类型不正确,必须为excel xlsx文件");
            }
        } catch (IOException e) {
            throw new Exception("文件解析失败");
        }
        Sheet sheetAt = workbook.getSheetAt(0);
        int count = sheetAt.getLastRowNum();
        if (count < 2) {
            throw new Exception("导入失败，excel文件数据为空！");
        }
        List<PlaneScheduleImport> feedbackImportList = new ArrayList<>();
        for (int i = 2; i <= count; i++) {
            Row row = sheetAt.getRow(i);
            if (row == null) {
                throw new Exception("第 " + (i + 1) + " 行数据不能为空");
            }
            FeedbackImport info = getFeedbackImport(row, channelPackages, channel, employeeDTO);
            feedbackImportList.add(info);
        }
    }
}
