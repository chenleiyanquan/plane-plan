package com.macro.mall.tiny.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PlaneSchedule;
import com.macro.mall.tiny.mbg.repository.PlaneRepository;
import com.macro.mall.tiny.service.PlaneScheduleListener;
import com.macro.mall.tiny.service.PlaneService;
import com.macro.mall.tiny.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
@Slf4j
public class PlaneScheduleController {
   // final static Logger logger = LoggerFactory.getLogger(PlaneScheduleController.class);

    @Autowired
    private PlaneService planeService;

    @Autowired
    private PlaneRepository planeRepository;


    @PostMapping("/import")
    @ResponseBody
    @ApiOperation(value = "1、导入原始数据")
    public CommonResult importInfo(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new Exception("参数不能为空");
        }
        try{
            planeService.clearAllData();
//            PlaneScheduleListener listener = new PlaneScheduleListener();
//            ExcelReaderBuilder builder =  EasyExcel.read(file.getInputStream(), PlaneScheduleExcelModel.class, listener);
//            ExcelReaderSheetBuilder sheetBuilder =builder.sheet();
//            sheetBuilder.doRead();
//            List<PlaneScheduleExcelModel> datas = listener.getList();
//            List<PlaneSchedule> planeScheduleList = convert(datas);
            if (file.getInputStream() == null) {
                throw new Exception("获取导入文件失败");
            }
            try {
                //这里需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
                EasyExcel.read(file.getInputStream(), PlaneScheduleExcelModel.class, new PlaneScheduleListener(planeRepository)).sheet().doRead();
            } catch (Exception e) {
                log.error("导入文件异常结束", e);
            }
            List<PlaneSchedule> allPlaneSchedules =  planeService.getAllPlanesByType(0);
            if(CollectionUtils.isEmpty(allPlaneSchedules)){
                throw new Exception("导入失败！请重新导入");
            }
            return CommonResult.success("导入成功！");
        }catch(Exception e){
            return  CommonResult.failed(e.getMessage());
        }
    }

//    @ApiOperation(value = "2、计算航班环增", notes = "计算航班环增")
//    @PostMapping(value = "/planeSchedule", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public CommonResult planeSchedule(@RequestBody PlaneScheduleReq planeScheduleReq) throws Exception {
//        //校验registration的总数是否为偶数，若不是，则给出提示
////        String checkMsg = planeService.checkDoubleRegistration();
////        if(!StringUtils.isEmpty(checkMsg)){
////            throw new Exception("导入数据中以下发动机编号数为奇数，请处理后重新导入："+checkMsg);
////        }
//        List<PerHourPlaneLimitReq> hourPlanes = planeScheduleReq.getHourPlanes();
//        hourPlanes =  hourPlanes.stream().sorted(Comparator.comparing(PerHourPlaneLimitReq::getPerHour)).collect(Collectors.toList());
//        SortedMap<Integer, Integer> map = new TreeMap<>();
//        hourPlanes.forEach(e->{
//            map.put(e.getPerHour(),e.getPlaneCount());
//        });
//        List<PlaneSchedule> allPlaneSchedules =  planeService.getAllPlanesByType(0);
//        if(CollectionUtils.isEmpty(allPlaneSchedules)){
//            throw new Exception("请先导入数据！");
//        }
//        Map<String,Integer> callsignMap = new HashMap<>();
//        Map<String,Integer> registrationMap = new HashMap<>();
//        List<Integer> excludeIdList = new ArrayList<>();
//        for(int i=0;i<allPlaneSchedules.size();i++){
//         PlaneSchedule e = allPlaneSchedules.get(i);
//        //for(PlaneSchedule e: allPlaneSchedules){
//            Integer hour = e.getAirtime().getHours();
//            if(map.get(hour)<1){
//                continue;
//            }
//           boolean result =  saveSameRegistrationData(map,e,callsignMap,planeScheduleReq.getIntervalTime(),"orgin",allPlaneSchedules,i,registrationMap);
//           if(result){
//               excludeIdList.add(e.getId());
//               Optional<PlaneSchedule> nextOpt = allPlaneSchedules.stream().filter(f->e.getRegistration().equals(f.getRegistration()) && !excludeIdList.contains(f.getId()) && f.getId()>e.getId()).findFirst();
//               if(nextOpt.isPresent()){
//                   saveSameRegistrationData(map,nextOpt.get(),callsignMap,planeScheduleReq.getIntervalTime(),"copy",allPlaneSchedules,i,registrationMap);
//               }
//           }
//        };
//        return CommonResult.success("处理成功！");
//    }

    @ApiOperation(value = "2、计算航班环增", notes = "计算航班环增")
    @PostMapping(value = "/newPlaneSchedule", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResult newPlaneSchedule(@RequestBody PlaneScheduleReq planeScheduleReq) throws Exception {
        //校验registration的总数是否为偶数，若不是，则给出提示
//        String checkMsg = planeService.checkDoubleRegistration();
//        if(!StringUtils.isEmpty(checkMsg)){
//            throw new Exception("导入数据中以下发动机编号数为奇数，请处理后重新导入："+checkMsg);
//        }
        List<PerHourPlaneLimitReq> hourPlanes = planeScheduleReq.getHourPlanes();
        hourPlanes =  hourPlanes.stream().sorted(Comparator.comparing(PerHourPlaneLimitReq::getPerHour)).collect(Collectors.toList());
        SortedMap<Integer, Integer> map = new TreeMap<>();
        SortedMap<Integer, Integer> backupMap = new TreeMap<>();
        SortedMap<Integer, Integer> resultMap = new TreeMap<>();
        SortedMap<Integer, Integer> hourIntervalTimeMap = new TreeMap<>();
        hourPlanes.forEach(e->{
            map.put(e.getPerHour(),e.getPlaneCount());
            backupMap.put(e.getPerHour(),e.getPlaneCount());
            resultMap.put(e.getPerHour(),0);
        });
        List<PlaneSchedule> allPlaneSchedules =  planeService.getAllPlanesByType(0);
        if(CollectionUtils.isEmpty(allPlaneSchedules)) {
            throw new Exception("请先导入数据！");
        }
        Map<Integer,List<PlaneSchedule>> planeHourGroupMap = allPlaneSchedules.stream().collect(Collectors.groupingBy(PlaneSchedule::getAirhour));
        Map<String,Integer> callsignMap = new HashMap<>();
        Map<String,Integer> registrationMap = new HashMap<>();
        List<Integer> excludeIdList = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            int num = 0;
            List<PlaneSchedule> hourList = planeHourGroupMap.get(entry.getKey());
            if(CollectionUtils.isEmpty(hourList)){
                continue;
            }
            hourList = hourList.stream().sorted(Comparator.comparing(PlaneSchedule::getId)).collect(Collectors.toList());
            boolean hourFlag = Boolean.TRUE;
            int maxCount = map.get(entry.getKey());
           while(hourFlag){
               for(int i=0;i<hourList.size();i++){
                   PlaneSchedule planeSchedule = hourList.get(i);
                    PlaneSchedule newPlane = new PlaneSchedule();
                   boolean result =  saveSameRegistrationDataNew(map,planeSchedule,callsignMap,planeScheduleReq.getIntervalTime(),"orgin",allPlaneSchedules,hourList,i,registrationMap,newPlane, null,hourIntervalTimeMap,resultMap);
                   num++;
                   if(result){
                       excludeIdList.add(planeSchedule.getId());
                       Optional<PlaneSchedule> nextOpt = allPlaneSchedules.stream().filter(f->planeSchedule.getRegistration().equals(f.getRegistration()) && !excludeIdList.contains(f.getId()) && f.getId()>planeSchedule.getId()).findFirst();
                       if(nextOpt.isPresent()){
                           PlaneSchedule lastSavePlane = newPlane;
                           newPlane = new PlaneSchedule();
                           saveSameRegistrationDataNew(map,nextOpt.get(),callsignMap,planeScheduleReq.getIntervalTime(),"copy",allPlaneSchedules,hourList,i,registrationMap,newPlane,lastSavePlane,hourIntervalTimeMap,resultMap);
                       }
                   }
               }
               if(entry.getValue()<1 || num>maxCount*2){
                   hourFlag = Boolean.FALSE;
               }
           }
        }
        StringBuffer stringBuffer = new StringBuffer();
        int planNum = 0;
        int actNum = 0;
        for (Map.Entry<Integer,Integer> entry : backupMap.entrySet()) {
            int key = entry.getKey();
            planNum += entry.getValue();
            actNum += resultMap.get(key);
           stringBuffer.append(key).append("点应增").append(entry.getValue()).append("架,实增").append(resultMap.get(key)).append("架;");
        }
        stringBuffer.append("累计应增").append(planNum).append("架，实增").append(actNum).append("架。");
        return CommonResult.success("处理成功！"+stringBuffer);
    }

    private boolean saveSameRegistrationData(SortedMap<Integer, Integer> map, PlaneSchedule planeSchedule, Map<String, Integer> callsignMap, int intervalTime, String type, List<PlaneSchedule> allPlaneSchedules, int index, Map<String, Integer> registrationMap) throws Exception {
        boolean afterExsitedFlag = allPlaneSchedules.stream().anyMatch(f->f.getId()>planeSchedule.getId() && f.getRegistration().equals(planeSchedule.getRegistration()));
        //判断本条数据之后的数据里，是否有同注册号的航班，若无，且在之前复制过同注册号的航班，则不复制本条数据
        if("orgin".equals(type) && registrationMap.containsKey(planeSchedule.getRegistration()) && !afterExsitedFlag){
            return false;
        }
        int hour = planeSchedule.getAirtime().getHours();
        int minutes = planeSchedule.getAirtime().getMinutes();
        int maxCount = map.get(hour);
        if(maxCount<1){
            if("orgin".equals(type)){
                return false;
            }else{
               // planeService.clearAllData();
                throw new Exception("第"+(planeSchedule.getId()+1)+"行，callsign："+planeSchedule.getCallsign()+",registration:"+planeSchedule.getRegistration()+",airTime:"+planeSchedule.getAirtime()+"，该时间段新增航班数超过设置新增航班数！请重新导入、调整该时间段的新增航班数后再操作！");
            }
        }
        //添加限制条件：复制前校验 如果将要复制的航班此前未复制，但在此前存在，则跳过
        if("orgin".equals(type)) {
            boolean beforeExisted = allPlaneSchedules.subList(0, index).stream().anyMatch(e -> e.getRegistration().equals(planeSchedule.getRegistration()));
            if (beforeExisted && !registrationMap.containsKey(planeSchedule.getRegistration())) {
                return false;
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
        registrationMap.put(planeSchedule.getRegistration(),version);
        return true;
    }

    //@Transactional(rollbackFor = Exception.class)
    public boolean saveSameRegistrationDataNew(SortedMap<Integer, Integer> map, PlaneSchedule planeSchedule,
                                               Map<String, Integer> callsignMap, int intervalTime, String type,
                                               List<PlaneSchedule> allPlaneSchedules, List<PlaneSchedule> hourList, int index,
                                               Map<String, Integer> registrationMap, PlaneSchedule newPlane, PlaneSchedule lastSavePlane, SortedMap<Integer, Integer> hourIntervalTimeMap, SortedMap<Integer, Integer> resultMap) throws Exception {
        try{
        //添加限制条件：复制前校验 如果将要复制的航班此前未复制，但在此前存在，则跳过
        if("orgin".equals(type)) {
            boolean afterExsitedFlag = allPlaneSchedules.stream().anyMatch(f->f.getId()>planeSchedule.getId() && f.getRegistration().equals(planeSchedule.getRegistration()));
            //判断本条数据之后的数据里，是否有同注册号的航班，若无，且在之前复制过同注册号的航班，则不复制本条数据
            if(registrationMap.containsKey(planeSchedule.getRegistration()) && !afterExsitedFlag){
                return false;
            }
            boolean beforeExisted = allPlaneSchedules.stream().anyMatch(e -> e.getId() < planeSchedule.getId() && e.getRegistration().equals(planeSchedule.getRegistration()));
            if (beforeExisted && !registrationMap.containsKey(planeSchedule.getRegistration())) {
                return false;
            }
        }
        int hour = planeSchedule.getAirtime().getHours();
        int minutes = planeSchedule.getAirtime().getMinutes();
        int maxCount = map.get(hour);
        if(maxCount<1){
            if("orgin".equals(type)){
                return false;
            }else{
               // planeService.clearAllData();
                //throw new Exception("第"+(planeSchedule.getId()+1)+"行，callsign："+planeSchedule.getCallsign()+",registration:"+planeSchedule.getRegistration()+",airTime:"+planeSchedule.getAirtime()+"，该时间段新增航班数超过设置新增航班数！请重新导入、调整该时间段的新增航班数后再操作！");
                for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
                    if(entry.getKey()==23 && map.get(23)<1){
                        //复制对应航班时，航班数已超需要新增的航班数，则不再复制该航班
                        if(lastSavePlane!=null){
                            log.info("lastSavePlane:{}", JSONObject.toJSONString(lastSavePlane));
                            map.put(lastSavePlane.getAirhour(),map.get(lastSavePlane.getAirhour())+1);
                            Integer rollBackVersion = callsignMap.get(lastSavePlane.getCallsign());
                            if(rollBackVersion == null){
                                return false;
                            }
                            rollBackVersion = rollBackVersion-1;
                            callsignMap.put(lastSavePlane.getCallsign(),rollBackVersion);
                            registrationMap.put(planeSchedule.getRegistration(),rollBackVersion);
                        }
                        return false;
                    }
                    if(entry.getKey()<=hour){
                        continue;
                    }
                    if(entry.getValue()<1){
                        continue;
                    }
                    hour = entry.getKey();
                    break;
                }
                BeanUtils.copyProperties(planeSchedule,newPlane);
                newPlane.setDataSource((byte) 1);
                int version =  callsignMap.get(planeSchedule.getCallsign())==null?1:callsignMap.get(planeSchedule.getCallsign())+1;
                newPlane.setCallsign(planeSchedule.getCallsign()+"_"+version);
                newPlane.setRegistration(planeSchedule.getRegistration()+"_"+version);
                newPlane.setAirtime(DateUtils.addHours(planeSchedule.getAirtime(),hour-planeSchedule.getAirtime().getHours()));
                planeService.savePlane(newPlane);

                resultMap.put(hour,resultMap.get(hour)+1);

                maxCount = map.get(hour);
                map.put(hour,maxCount-1);
                callsignMap.put(planeSchedule.getCallsign(),version);
                registrationMap.put(planeSchedule.getRegistration(),version);
                return false;
            }
        }
        //PlaneSchedule newPlane = new PlaneSchedule();
        BeanUtils.copyProperties(planeSchedule,newPlane);
        newPlane.setDataSource((byte) 1);
        int version =  callsignMap.get(planeSchedule.getCallsign())==null?1:callsignMap.get(planeSchedule.getCallsign())+1;
        newPlane.setCallsign(planeSchedule.getCallsign()+"_"+version);
        newPlane.setRegistration(planeSchedule.getRegistration()+"_"+version);
        Integer interval = hourIntervalTimeMap.get(hour);
        if(interval==null){
            interval = (60-minutes)/maxCount == 0?3:(60-minutes)/maxCount;
            hourIntervalTimeMap.put(hour,interval);
        }
        if(minutes+interval*version>59){
            newPlane.setAirtime(DateUtils.addMinutes(planeSchedule.getAirtime(),interval*version*-1));
        }else{
            newPlane.setAirtime(DateUtils.addMinutes(planeSchedule.getAirtime(),interval*version));
        }
        planeService.savePlane(newPlane);
        resultMap.put(hour,resultMap.get(hour)+1);
        System.out.println("id:"+newPlane.getId()+"  version:"+version+ "  interval: "+interval);
        map.put(hour,maxCount-1);
        callsignMap.put(planeSchedule.getCallsign(),version);
        registrationMap.put(planeSchedule.getRegistration(),version);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @ApiOperation(value = "3、导出航班数据", produces = "application/octet-stream", notes = "点击下载蓝色按钮下载 <a href='http://124.221.144.226:8088/air/toDownloadExcel' /a>下载")
    @RequestMapping(value = "toDownloadExcel", method = RequestMethod.GET)
    public void toDownloadExcel(HttpServletResponse response) throws IOException {
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
        log.info("航班数据导出为：{}", list);
        writer.write(list, sheet1);
        writer.finish();
        out.flush();
    }

}
