package com.macro.mall.tiny.service;//package com.ziku.msp.canyin.rider.web;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ziku.msb.common.base.Response;
//import com.ziku.msp.canyin.extModel.PerHourPlane;
//import com.ziku.msp.canyin.extModel.PerHourPlaneCount;
//import com.ziku.msp.canyin.model.PlaneScheduleExport;
//import com.ziku.msp.canyin.rider.request.AirLineHourData;
//import com.ziku.msp.canyin.rider.request.IncrementDetail;
//import com.ziku.msp.canyin.rider.request.PerHourData;
//import com.ziku.msp.canyin.rider.request.PlaneReq;
//import com.ziku.msp.canyin.rider.service.PlaneService;
//import com.ziku.msp.canyin.util.NotRequiredPermission;
//import com.ziku.msp.canyin.utils.BigDecimalUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.util.*;
//
///*
// *
// * @Description:
// *
// * @auther: chenlei
// * @date: 19:51 2018/10/9
// * @param:
// * @return:
// *
// */
//@Api(tags = "航班")
//@RequestMapping("/air")
//@RestController
//public class PlaneController {
//    final static Logger logger = LoggerFactory.getLogger(PlaneController.class);
//
//    @Autowired
//    private PlaneService planeService;
//
//    @ApiOperation(value = "航班环增", notes = "航班环增")
//    @NotRequiredPermission(value="pass")
//    @PostMapping(value = "/vertify", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Response<Object> vertify(@RequestBody PlaneReq planeReq) {
//        //   logger.info("RiderController.vertify request:{}", JSONObject.toJSONString(vertifyReq));
//        //获取原航班总数
//        long total = planeService.getTotalPlaneCount(0);
//        System.out.println("total:"+total);
//        //获取航空公司数
//        long airPlaneConut = planeService.getAirPlaneConut(0);
//        Map<String,Integer> specialAirPlanes = new HashMap<>();
//        //指定增量的公司 增量总和
//        int specialIncre = 0;
//        if(planeReq.getIncrementDetails()!=null && planeReq.getIncrementDetails().size()>0){
//            for(IncrementDetail req:planeReq.getIncrementDetails()){
//                int tempCount = (int) Math.round(total*req.getIncrementRate());
//                specialAirPlanes.put(req.getCompanyName(),tempCount);
//                specialIncre = specialIncre+ tempCount;
//            }
//        }
//        // 按增量比例计算增量(除不尽时向上取整)
//        int increment = (int) Math.round(total * planeReq.getIncrementRate());
//        System.out.println("增量："+increment);
//        //非指定增量公司 平均增量
//        long avgIncre = 0 ;
//        long unAvg = (increment-specialIncre)%(airPlaneConut-planeReq.getIncrementDetails().size());
//        if(unAvg ==0){
//            //剩余增量刚好平分,每个航空公司的增量
//            avgIncre = (increment-specialIncre)/(airPlaneConut-planeReq.getIncrementDetails().size());
//        }else{
//            //剩余增量未平分，,每个航空公司的增量
//            avgIncre = (increment-specialIncre-unAvg)/(airPlaneConut-planeReq.getIncrementDetails().size());
//        }
//
//        //高峰小时起飞数(除不尽时向下取整)
//        int maxDepart = (int) Math.round(planeReq.getMax()*0.5);
//        //高峰小时到达数(除不尽时向下取整)
//        int maxArrive = maxDepart;
//
//        //查询原安排表 （每个航空公司每小时到达航班数）列表
//        List<PerHourPlane> arrives =  planeService.getPerHourArrivePlanes();
//        List<AirLineHourData> globalList = new ArrayList<>();
//        List<AirLineHourData> remainList = new ArrayList<>();
//        Set<String> airLineSet = new HashSet<>();
//        arrives.forEach(arrive->{
//            AirLineHourData data = new AirLineHourData();
//            BeanUtils.copyProperties(arrive,data);
//            data.setArriveCount(arrive.getPlaneCount());
//            globalList.add(data);
//            airLineSet.add(arrive.getAirline()+arrive.getPerHour());
//            // System.out.println(arrive.getAirline()+":"+arrive.getPerHour()+":"+arrive.getPlaneCount());
//        });
//        ////查询原安排表 （每个航空公司每小时起飞航班数）
//        List<PerHourPlane> departs = planeService.getPerHourDepartPlanes();
//        //合并数据，每小时每个航空公司起飞、到达航班数
//        globalList.forEach(g->{
//            departs.forEach(depart->{
//                if(g.getAirline().equals(depart.getAirline()) && g.getPerHour()== depart.getPerHour()){
//                    g.setDepartCount(depart.getPlaneCount());
//                }
//                if(!airLineSet.contains(depart.getAirline()+depart.getPerHour())) {
//                    AirLineHourData data = new AirLineHourData();
//                    BeanUtils.copyProperties(depart,data);
//                    data.setArriveCount(0);
//                    data.setDepartCount(depart.getPlaneCount());
//                    remainList.add(data);
//                    airLineSet.add(depart.getAirline()+depart.getPerHour());
//                }
//            });
//            // System.out.println(depart.getAirline()+":"+depart.getPerHour()+":"+depart.getPlaneCount());
//        });
//        //合并数据
//        globalList.addAll(remainList);
//        //按照数量从小到大排序
//        Collections.sort(globalList, new Comparator<AirLineHourData>() {
//            @Override
//            public int compare(AirLineHourData o1, AirLineHourData o2) {
//                if (o1.getPerHour() > o2.getPerHour()) {
//                    return 1;
//                }
//                if (o1.getPerHour() == o2.getPerHour()) {
//                    return 0;
//                }
//                return -1;
//            }
//        });
//
//        //计算每小时起飞、到达航班数、剩余容量
//        List<PerHourPlaneCount> arrivesCount= planeService.getPerHourArrivePlanesCount();
//        List<PerHourData> globalCountList = new ArrayList<>();
//        List<PerHourData> remainCountList = new ArrayList<>();
//        Set<Integer> airLineCountSet = new HashSet<>();
//        arrivesCount.forEach(arrive->{
//            PerHourData data = new PerHourData();
//            data.setArriveCount(arrive.getPlaneCount());
//            data.setPerHour(arrive.getPerHour());
//            data.setRemainArriveCount(maxArrive-arrive.getPlaneCount());
//            globalCountList.add(data);
//            airLineCountSet.add(arrive.getPerHour());
//        });
//        List<PerHourPlaneCount> departsCount = planeService.getPerHourDepartPlanesCount();
//        globalCountList.forEach(g->{
//            departsCount.forEach(depart->{
//                if(g.getPerHour()==depart.getPerHour()){
//                    g.setDepartCount(depart.getPlaneCount());
//                    g.setRemainDepartCount(maxDepart-depart.getPlaneCount());
//                }
//                if(!airLineCountSet.contains(depart.getPerHour())){
//                    PerHourData data = new PerHourData();
//                    data.setArriveCount(0);
//                    data.setRemainArriveCount(maxArrive);
//                    data.setPerHour(depart.getPerHour());
//                    data.setDepartCount(depart.getPlaneCount());
//                    data.setRemainDepartCount(maxDepart-depart.getPlaneCount());
//                    remainCountList.add(data);
//                    airLineCountSet.add(depart.getPerHour());
//                }
//            });
//        });
//        globalCountList.addAll(remainCountList);
//        //按照数量从小到大排序
//        Collections.sort(globalCountList, new Comparator<PerHourData>() {
//            @Override
//            public int compare(PerHourData o1, PerHourData o2) {
//                if (o1.getPerHour() >= o2.getPerHour()) {
//                    return 1;
//                }
//                if (o1.getPerHour() == o2.getPerHour()) {
//                    return 0;
//                }
//                return -1;
//            }
//        });
//        int tempTotal = 0;
//        int tempTotal2 = 0;
//        for(PerHourData gg:globalCountList){
//        //    System.out.println(gg.getDepartCount()+gg.getArriveCount());
//            // 创建一个数值格式化对象
//            NumberFormat numberFormat = NumberFormat.getInstance();
//            // 设置精确到小数点后2位
//            numberFormat.setMaximumFractionDigits(2);
//            tempTotal2 = tempTotal2+ gg.getDepartCount()+gg.getArriveCount();
//            //String result =  numberFormat.format((double)(gg.getDepartCount()+gg.getArriveCount())/(double)total);
//            String departResult =  numberFormat.format((double)(gg.getDepartCount())/(double)total);
//            String arrResult =  numberFormat.format((double)(gg.getArriveCount())/(double)total);
//        //    System.out.println((int) Math.round(increment*Double.valueOf(result)));
//            gg.setArrIncre((int) Math.ceil(increment*Double.valueOf(arrResult)));
//            gg.setDepIncre((int) Math.ceil(increment*Double.valueOf(departResult)));
//            tempTotal += gg.getArrIncre()+gg.getDepIncre();
//            System.out.println(JSONObject.toJSON(gg));
//        }
//       // System.out.println("tempTotal2:"+tempTotal2);
//
//        System.out.println("tempTotal:"+tempTotal);
//        boolean flag = false;
//        BigDecimal rebackRate = BigDecimal.ZERO;
//        BigDecimal finnalRate = BigDecimal.ONE;
//        if(increment>tempTotal){
//            //计算出的新增量少于要求新增量
//            rebackRate = BigDecimalUtils.divide(new BigDecimal(increment-tempTotal),new BigDecimal(tempTotal));
//            finnalRate = BigDecimalUtils.add(finnalRate,rebackRate).setScale(4, BigDecimal.ROUND_HALF_UP);
//        }else if(increment<tempTotal){
//            //计算出的新增量多于要求新增量
//            rebackRate = BigDecimalUtils.divide(new BigDecimal(tempTotal-increment),new BigDecimal(tempTotal));
//            finnalRate = BigDecimalUtils.subtract(finnalRate,rebackRate).setScale(4, BigDecimal.ROUND_HALF_UP);
//
//        }
//        planeReq.setManualRate(finnalRate.doubleValue());
//      /*  if(increment<tempTotal){
//            globalCountList.forEach(gc->{
//                globalList.forEach(gg->{
//                    if(gc.getPerHour()== gg.getPerHour()){
//                        gg.setArriveIncrement((int) Math.ceil((double)gg.getArriveCount()/(double)gc.getArriveCount()*gc.getArrIncre()));
//                        gg.setDepartIncrement((int) Math.ceil((double)gg.getDepartCount()/(double)gc.getDepartCount()*gc.getDepIncre()));
//                    }
//                });
//            });
//        }else{
//            globalCountList.forEach(gc->{
//                globalList.forEach(gg->{
//                    if(gc.getPerHour()== gg.getPerHour()){
//                        gg.setArriveIncrement((int) Math.floor((double)gg.getArriveCount()/(double)gc.getArriveCount()*gc.getArrIncre()));
//                        gg.setDepartIncrement((int) Math.floor((double)gg.getDepartCount()/(double)gc.getDepartCount()*gc.getDepIncre()));
//                    }
//                });
//            });
//        }*/
//        globalCountList.forEach(gc->{
//            globalList.forEach(gg->{
//                if(gc.getPerHour()== gg.getPerHour()){
//                    gg.setArriveIncrement((int) Math.ceil((double)gg.getArriveCount()/(double)gc.getArriveCount()*gc.getArrIncre()*planeReq.getManualRate()));
//                    gg.setDepartIncrement((int) Math.ceil((double)gg.getDepartCount()/(double)gc.getDepartCount()*gc.getDepIncre()*planeReq.getManualRate()));
//                }
//            });
//        });
//        int arrIncraTotal = 0;
//        int depIncraTotal = 0;
//        for(AirLineHourData data :globalList){
//            arrIncraTotal = arrIncraTotal +data.getArriveIncrement();
//            depIncraTotal = depIncraTotal +data.getDepartIncrement();
//        }
//        System.out.println("arrIncraTotal:"+arrIncraTotal);
//        System.out.println("depIncraTotal:"+depIncraTotal);
//        planeService.transcateExport();
//        for(AirLineHourData data:globalList){
//            System.out.println(JSONObject.toJSON(data));
//            PlaneScheduleExport export = new PlaneScheduleExport();
//          //  export.setArrtime();
//           // export.setCallsign();
//            export.setAirline(data.getAirline());
//            export.setDataSource((byte)1);
//          //  export.setDeptime();
//           // export.setDestination();
//           // export.setOrigin();
//         //   export.setRegistration();
//          //  export.setRouting();
//          //  planeService.addPlaneExport(export);
//
//        }
//
//
////        for(int i=0;i<24;i++){
////
////        }
//        return null;
//    }
//
//
//
//
//}
