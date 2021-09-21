package com.macro.mall.tiny.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.service.PlaneScheduleListener;
import com.macro.mall.tiny.service.PlaneService;
import com.macro.mall.tiny.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.java.Log;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/*
 *
 * @Description:
 *
 * @auther: chenlei
 * @date: 19:51 2018/10/9
 * @param:
 * @return:
 *
 */
@Api(tags = "航班计划")
@RequestMapping("/air")
@RestController
@Log
public class PlaneScheduleController {
    final static Logger logger = LoggerFactory.getLogger(PlaneScheduleController.class);

    @Autowired
    private PlaneService planeService;

    @ApiOperation(value = "导出航班数据", produces = "application/octet-stream", notes = "导出航班数据")
    @RequestMapping(value = "downExcel", method = RequestMethod.GET)
    public void downExcel(HttpServletResponse response) throws IOException {
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        String fileName = "airplaneData_" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date())) + ".xlsx";
       // fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();

        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
        Sheet sheet1 = new Sheet(1, 0, PlaneScheduleExcel.class);
        sheet1.setSheetName("sheet1");
        //设置自适应宽度
        sheet1.setAutoWidth(Boolean.TRUE);

        List<PlaneScheduleExcel> list = planeService.getPlanesExcelVos();
        logger.info("航班数据导出为：{}", list);
        writer.write(list, sheet1);
        writer.finish();

        out.flush();

    }

    @PostMapping("/import")
    @ResponseBody
    @ApiOperation("导入反馈开通情况")
    public CommonResult importInfo(MultipartFile file) throws Exception {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new Exception("参数不能为空");
        }
        try{
            planeService.clearAllData();
            PlaneScheduleListener listener = new PlaneScheduleListener();
            ExcelReaderBuilder builder =  EasyExcel.read(file.getInputStream(), PlaneScheduleExcelModel.class, listener);
            ExcelReaderSheetBuilder sheetBuilder =builder.sheet();
            sheetBuilder.doRead();
            List<PlaneScheduleExcelModel> datas = listener.getList();
            List<PlaneSchedule> planeScheduleList = convert(datas);
            planeService.batchInsert(planeScheduleList);
            //校验registration的总数是否为偶数，若不是，则给出提示
            String checkMsg = planeService.checkDoubleRegistration();
            if(!StringUtils.isEmpty(checkMsg)){
                throw new Exception("导入数据中以下发动机编号数为奇数，请处理后重新导入："+checkMsg);
            }
            return CommonResult.success("导入成功！");
        }catch(Exception e){
            return  CommonResult.failed(e.getMessage());
        }
    }

    private List<PlaneSchedule> convert(List<PlaneScheduleExcelModel> datas) {
        if(CollectionUtils.isEmpty(datas)){
            return new ArrayList<PlaneSchedule>() ;
        }
        return datas.stream().map(e->{
            PlaneSchedule planeSchedule = new PlaneSchedule();
            BeanUtils.copyProperties(e,planeSchedule);
            return planeSchedule;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "航班环增", notes = "航班环增")
    @PostMapping(value = "/planeSchedule", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResult planeSchedule(@RequestBody PlaneScheduleReq planeScheduleReq) throws Exception {
        //校验registration的总数是否为偶数，若不是，则给出提示
//        String checkMsg = planeService.checkDoubleRegistration();
//        if(!StringUtils.isEmpty(checkMsg)){
//            throw new Exception("导入数据中以下发动机编号数为奇数，请处理后重新导入："+checkMsg);
//        }
        List<PerHourPlaneLimitReq> hourPlanes = planeScheduleReq.getHourPlanes();
        hourPlanes =  hourPlanes.stream().sorted(Comparator.comparing(PerHourPlaneLimitReq::getPerHour)).collect(Collectors.toList());
        SortedMap<Integer, Integer> map = new TreeMap<>();
        hourPlanes.forEach(e->{
            map.put(e.getPerHour(),e.getPlaneCount());
        });
        List<PlaneSchedule> allPlaneSchedules =  planeService.getAllPlanesByType(0);
        if(CollectionUtils.isEmpty(allPlaneSchedules)){
            throw new Exception("请先导入数据！");
        }
        Map<String,Integer> callsignMap = new HashMap<>();
        List<Integer> excludeIdList = new ArrayList<>();
        for(PlaneSchedule e: allPlaneSchedules){
            Integer hour = e.getAirtime().getHours();
            if(map.get(hour)<1){
                continue;
            }
           boolean result =  saveSameRegistrationData(map,e,callsignMap,planeScheduleReq.getIntervalTime(),"orgin");
           if(result){
               excludeIdList.add(e.getId());
               Optional<PlaneSchedule> nextOpt = allPlaneSchedules.stream().filter(f->e.getRegistration().equals(f.getRegistration()) && !excludeIdList.contains(f.getId()) && f.getId()>e.getId()).findFirst();
               if(nextOpt.isPresent()){
                   saveSameRegistrationData(map,nextOpt.get(),callsignMap,planeScheduleReq.getIntervalTime(),"copy");
               }
           }
        };
        return CommonResult.success("处理成功！");
    }

    private boolean saveSameRegistrationData(SortedMap<Integer, Integer> map, PlaneSchedule planeSchedule, Map<String, Integer> callsignMap, int intervalTime,String type) throws Exception {
        int hour = planeSchedule.getAirtime().getHours();
        int minutes = planeSchedule.getAirtime().getMinutes();
        int maxCount = map.get(hour);
        if(maxCount<1){
            if("orgin".equals(type)){
                return false;
            }else{
                throw new Exception("第"+(planeSchedule.getId()+1)+"行，callsign："+planeSchedule.getCallsign()+",registration:"+planeSchedule.getRegistration()+",airTime:"+planeSchedule.getAirtime()+"，该时间段航班数超限！");
            }
        }
        PlaneSchedule newPlane = new PlaneSchedule();
        BeanUtils.copyProperties(planeSchedule,newPlane);
        newPlane.setDataSource((byte) 1);
        int version =  callsignMap.get(planeSchedule.getCallsign())==null?1:callsignMap.get(planeSchedule.getCallsign())+1;
        newPlane.setCallsign(planeSchedule.getCallsign()+"_"+version);
        newPlane.setRegistration(planeSchedule.getRegistration()+"_"+version);
        if(minutes+intervalTime>60){
            newPlane.setAirtime(DateUtils.addMinutes(planeSchedule.getAirtime(),intervalTime*-1));
        }else{
            newPlane.setAirtime(DateUtils.addMinutes(planeSchedule.getAirtime(),intervalTime));
        }
        planeService.savePlane(newPlane);
        map.put(hour,maxCount-1);
        callsignMap.put(planeSchedule.getCallsign(),version);
        return true;
    }

}
