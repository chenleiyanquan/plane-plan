package com.macro.mall.tiny.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
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
import lombok.extern.java.Log;
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

    @ApiOperation(value = "航班环增", notes = "航班环增")
    @PostMapping(value = "/plane", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String vertify(@RequestBody PlaneReq planeReq) throws ParseException {
        /**
         * 第一步 计算 每小时每个航空公司新增多少起飞的航班、降落的航班
         */

        //获取原航班总数
        long total = planeService.getTotalPlaneCount(0);
        logger.info("total 获取原航班总数{}:",total);
        //获取航空公司数
        long airPlaneConut = planeService.getAirPlaneConut(0);
        List<AirLineHourData> orginList = calPerHourPlanes(planeReq, total);
        if(!CollectionUtils.isEmpty(orginList)){
            for(AirLineHourData data : orginList){
                logger.info("************************"+data.getPerHour()+"点到达航班结界开始***********************");
                logger.info(JSONObject.toJSONString(data));
                List<PlaneSchedule> perHourList =   planeService.getPlanesByArriveHour(data.getPerHour());
                Map<String,Long> planesByAirlineMap = perHourList.stream().collect(groupingBy(PlaneSchedule::getAirline, counting()));
                //Map<String, Long> result = sortMapByValue(planesByAirlineMap,6);
//                Map<String, Long> result = new LinkedHashMap<>();
//                planesByAirlineMap.entrySet().stream()
//                                 .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                                 .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
                perHourList = perHourList.stream().sorted(Comparator.comparing(PlaneSchedule::getAirline)).collect(Collectors.toList());
                logger.info("------------------------------------------------------");
                int arriveCountLimit = data.getArriveIncrement();
                    if(!CollectionUtils.isEmpty(perHourList)){
                        Map<String, Long> result = sortMapByValue(planesByAirlineMap,arriveCountLimit);
                        int resultSize = result.size();
                        int tempCount = 0;
                        boolean addFlag = false;
                        for(PlaneSchedule planeSchedule:perHourList){
                            addFlag = false;
                            tempCount ++;
                            logger.info("perHourList.size:{},当前位置：{}",perHourList.size() ,tempCount);
                            logger.info(JSONObject.toJSONString(planeSchedule));
                            if(arriveCountLimit <= 0 ){
                                break;
                            }
                            if(planeSchedule.getRegistration().contains("#")){
                                continue;
                            }
                            Set<String> strSet = new HashSet<>();
                            logger.info("航空公司出现频率从高到低：{}",result);
                            //在频率高的公司中选择航班复制
                            for(String key:result.keySet()){
                                if(data.getArriveIncrement()< resultSize && strSet.contains(key)){
                                    //如果要求的增长数比 公司数少，每个公司增长一个
                                    result.remove(key);
                                    continue;
                                }
                                //根据航空公司、时间查找 航班列表
                                //   logger.info("航空公司key:{}",key);
                                if(key.equals(planeSchedule.getAirline())){
                                    //复制到达
                                    PlaneSchedule newPlane = new PlaneSchedule();
                                    BeanUtils.copyProperties(planeSchedule,newPlane);
                                    newPlane.setRegistration(planeSchedule.getRegistration()+"#1");
                                    newPlane.setCallsign(planeSchedule.getCallsign()+"#1");
                                    newPlane.setId(null);
                                    newPlane.setInsertTime(System.currentTimeMillis());
                                    planeService.savePlane(newPlane);
                                    logger.info("newPlane:{}",JSONObject.toJSONString(newPlane));
                                    //复制对应出发
                                    //查找该航班后面时段里的对应出发
                                    PlaneSchedule  planeScheduleImage =  planeService.getPlaneScheduleImage(planeSchedule);
                                    if(planeScheduleImage!=null){
                                        logger.info("planeScheduleImage:{}",JSONObject.toJSONString(planeScheduleImage));
                                        PlaneSchedule newImagePlane = new PlaneSchedule();
                                        BeanUtils.copyProperties(planeScheduleImage,newImagePlane);
                                        newImagePlane.setRegistration(planeScheduleImage.getRegistration()+"#1");
                                        newImagePlane.setCallsign(planeScheduleImage.getCallsign()+"#1");
                                        newImagePlane.setId(null);
                                        newImagePlane.setInsertTime(System.currentTimeMillis());
                                        planeService.savePlane(newImagePlane);
                                    }
                                    addFlag = true;
                                    strSet.add(key);
                                }
                                //List<PlaneSchedule> hourList =   planeService.getPlanesByHourAndAir(data.getPerHour());
                            }
                            perHourList =   planeService.getPlanesByArriveHour(data.getPerHour());
                            if(addFlag){
                                arriveCountLimit --;
                            }
                        }
                    }
                logger.info("************************{}点到达航班结界结束***************************",+data.getPerHour());
                logger.info("\n\n");
            }
            //到达统计完毕，统计已增到达、起飞数，为复制剩余起飞做准备
            List<IncrementVo> incrementVos =  planeService.getIncrementedCount();
            for(AirLineHourData data : orginList){
                for(IncrementVo incrementVo:incrementVos){
                    if(data.getPerHour()== incrementVo.getPerHour()){
                        data.setArriveIncremented(incrementVo.getArriveIncremented());
                        data.setDepartIncremented(incrementVo.getDepartIncremented());
                        data.setDepartRemainIncremented(data.getDepartIncrement()-incrementVo.getDepartIncremented());
                    }
                }
                logger.info("复制完到达后的航班情况：{}",JSONObject.toJSONString(data));
            }

            //复制起飞
            for(AirLineHourData data : orginList){
                int remainCount = data.getDepartRemainIncremented();
               if(remainCount>0){
                   List<PlaneSchedule> perHourDepList = planeService.getPlanesByDepHour(data.getPerHour());
                   List<PlaneSchedule>   perHourDepSubList =  perHourDepList.stream().filter(item->item.getRegistration().contains("#")).collect(Collectors.toList());
                   if(perHourDepList.size()-perHourDepSubList.size()>remainCount){
                       perHourDepList.removeAll(perHourDepSubList);
                       perHourDepList = perHourDepList.stream().sorted(Comparator.comparing(PlaneSchedule::getAirline)).collect(Collectors.toList());
                       Map<String,Long> planesByAirMap = perHourDepList.stream().collect(groupingBy(PlaneSchedule::getAirline, counting()));
                           Map<String, Long> sortResult = sortMapByValue(planesByAirMap,remainCount);
                           int resultSize = sortResult.size();
                       int tempCount = 0;
                       boolean addFlag = false;
                       for(PlaneSchedule planeSchedule:perHourDepList){
                           addFlag = false;
                           tempCount ++;
                           logger.info("perHourDepList.size:{},当前位置：{}",perHourDepList.size() ,tempCount);
                           logger.info(JSONObject.toJSONString(planeSchedule));
                           if(remainCount <= 0 ){
                               break;
                           }
                           if(planeSchedule.getRegistration().contains("#")){
                               continue;
                           }
                           Set<String> strSet = new HashSet<>();
                           logger.info("航空公司出现频率从高到低：{}",sortResult);
                           //在频率高的公司中选择航班复制
                           for(String key:sortResult.keySet()){
                               if(data.getDepartRemainIncremented()< resultSize && strSet.contains(key)){
                                   //如果要求的增长数比 公司数少，每个公司增长一个
                                   sortResult.remove(key);
                                   continue;
                               }
                               //根据航空公司、时间查找 航班列表
                               if(key.equals(planeSchedule.getAirline())){
                                   //复制起飞
                                   PlaneSchedule newPlane = new PlaneSchedule();
                                   BeanUtils.copyProperties(planeSchedule,newPlane);
                                   newPlane.setRegistration(planeSchedule.getRegistration()+"#1");
                                   newPlane.setCallsign(planeSchedule.getCallsign()+"#1");
                                   newPlane.setId(null);
                                   newPlane.setInsertTime(System.currentTimeMillis());
                                   planeService.savePlane(newPlane);
                                   logger.info("newPlane:{}",JSONObject.toJSONString(newPlane));
                                   addFlag = true;
                                   strSet.add(key);
                               }
                           }
                           if(addFlag){
                               remainCount --;
                           }
                       }
                   }
//                   perHourDepSubList.stream().forEach(item->{
//                       logger.info(JSONObject.toJSON(item));
//                   });
               }
            }
            //全部复制完后的结果
            List<IncrementVo> vos =  planeService.getIncrementedCount();
                for(IncrementVo incrementVo:vos){
                    logger.info("全部复制完后的新增航班情况：{}",JSONObject.toJSON(incrementVo));
                }

        }
        /**
         * 第二步 复制航班 从0点开始循环 找到
         */
        return null;
    }

    //Map 按value值从大到小排序，并取前10
    public static Map<String, Long> sortMapByValue(Map<String, Long> map,int size) {
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        List<Map.Entry<String, Long>> lists = new ArrayList<Map.Entry<String, Long>>(map.entrySet());
        Collections.sort(lists,new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1,Map.Entry<String, Long> o2) {
                long q1=o1.getValue();
                long q2=o2.getValue();
                long p=q2-q1;
                if(p>0){
                    return 1;
                }
                else if(p==0){
                    return 0;
                }
                else
                    return -1;
            }
        });
        if(lists.size()>=size){
            for(Map.Entry<String, Long> set:lists.subList(0, size)){
                sortedMap.put(set.getKey(), set.getValue());
            }
        }else {
            for(Map.Entry<String, Long> set:lists){
                sortedMap.put(set.getKey(), set.getValue());
            }
        }
        return sortedMap;
    }


    /**
     * 计算 每小时每个航空公司新增多少起飞的航班、降落的航班
     * @param planeReq
     * @param total
     * @return
     */
    private List<AirLineHourData> calPerHourPlanes(@RequestBody PlaneReq planeReq, long total) {
        // 按增量比例计算增量(除不尽时向上取整)
        int increment = (int) Math.ceil(total * planeReq.getIncrementRate());
        logger.info("要求增加的航班总量increment：{}",increment);

        //查询原安排表 （每个航空公司每小时到达航班数）列表
        List<PerHourPlane> arrives =  planeService.getPerHourArrivePlanes();
        List<AirLineHourData> globalList = new ArrayList<>();
        List<AirLineHourData> remainList = new ArrayList<>();
        Set<String> airLineSet = new HashSet<>();
        arrives.forEach(arrive->{
            AirLineHourData data = new AirLineHourData();
            BeanUtils.copyProperties(arrive,data);
            data.setArriveCount(arrive.getPlaneCount());
            globalList.add(data);
            airLineSet.add(String.valueOf(arrive.getPerHour()));
            logger.info("arrive.getAirline() 1 perHour:{},planeCount:{}",arrive.getPerHour(),arrive.getPlaneCount());
        });
        ////查询原安排表 （每小时起飞航班数）
        List<PerHourPlane> departs = planeService.getPerHourDepartPlanes();
        //合并数据，每小时起飞、到达航班数
        globalList.forEach(g->{
            departs.forEach(depart->{
                if(g.getPerHour()== depart.getPerHour()){
                    g.setDepartCount(depart.getPlaneCount());
                }
                if(!airLineSet.contains(String.valueOf(depart.getPerHour()))) {
                    AirLineHourData data = new AirLineHourData();
                    BeanUtils.copyProperties(depart,data);
                    data.setArriveCount(0);
                    data.setDepartCount(depart.getPlaneCount());
                    remainList.add(data);
                    airLineSet.add(String.valueOf(depart.getPerHour()));
                }
                logger.info("arrive.getAirline() 2 perHour:{},planeCount:{}",depart.getPerHour(),depart.getPlaneCount());
            });

        });
        //合并数据
        globalList.addAll(remainList);
        //按照数量从小到大排序
        Collections.sort(globalList, new Comparator<AirLineHourData>() {
            @Override
            public int compare(AirLineHourData o1, AirLineHourData o2) {
                if (o1.getPerHour() > o2.getPerHour()) {
                    return 1;
                }
                if (o1.getPerHour() == o2.getPerHour()) {
                    return 0;
                }
                return -1;
            }
        });
        int num = 0;
        int num2 = 0;
        for(AirLineHourData data:globalList){
            num += data.getArriveCount()+data.getDepartCount();
            data.setDepartIncrement(((int) Math.round(increment*data.getDepartCount()*1.01/total)));
            data.setArriveIncrement(((int) Math.round(increment*data.getArriveCount()*1.01/total)));
            num2 += data.getArriveIncrement()+data.getDepartIncrement();
            logger.info("data:{}",JSONObject.toJSONString(data));
        }
        logger.info("原航班总量num:{},航班实际增量num2:{}",num,num2);
        return  globalList;
    }


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
    public CommonResult importInfo(@RequestParam MultipartFile file) throws Exception {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new Exception("参数不能为空");
        }
        try{
            ExcelReader excelReader = EasyExcel.read(file.getInputStream(), PlaneScheduleExcelModel.class, new PlaneScheduleListener(planeService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            ReadSheet readSheet1 = EasyExcel.readSheet(1).build();
            excelReader.read(readSheet);
            excelReader.finish();
            return CommonResult.success("导入成功！");
        }catch(Exception e){
            return  CommonResult.failed(e.getMessage());
        }
    }
}
