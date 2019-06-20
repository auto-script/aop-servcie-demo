//package cn.mlick.aopservicedemo.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.envision.apim.kong.*;
//import org.springframework.util.CollectionUtils;
//
///**
// * @author lixiangxin
// * @date 2019/4/2 19:24
// */
//public class BatchDeleteKong {
//
////  String url = "http://10.27.20.143:8001";
//    String url = "http://10.27.20.193:8001";
//
//  public void init() {
//    AbstractKong.initKongUrl(url);
//  }
//
//  /** 删除所有kong的组件信息 */
//  public void deleteAllKong() {
//
//    String r = KongServices.builder().build().listAll();
//    JSONObject j = JSON.parseObject(r);
//    JSONArray s = j.getJSONArray("data");
//
//    for (int i = 0; i < s.size(); i++) {
//      JSONObject sItem = s.getJSONObject(i);
//
//      String sId = sItem.getString("id");
//      String rs = KongRoutes.builder().service(sId).build().listAllByServiceId();
//      JSONObject routes = JSON.parseObject(rs);
//      JSONArray routesList = routes.getJSONArray("data");
//      for (int k = 0; k < routesList.size(); k++) {
//        KongRoutes.builder().id(routesList.getJSONObject(k).getString("id")).build().delete(false);
//      }
//
//      String host = sItem.getString("host");
//      String r3 = KongTargets.builder().upstreamName(host).build().listByUpstreamName();
//      JSONObject targets = JSON.parseObject(r3);
//      JSONArray targetsList = targets.getJSONArray("data");
//      if (!CollectionUtils.isEmpty(targetsList)) {
//        for (int k = 0; k < targetsList.size(); k++) {
//          KongTargets.builder()
//              .id(targetsList.getJSONObject(k).getString("id"))
//              .upstream("")
//              .build()
//              .delete(false);
//        }
//      }
//      KongUpstreams.builder().name(host).build().delete(false);
//
//      KongServices.builder().id(sId).build().delete(false);
//    }
//  }
//
//  public void deleteAllUpstream() {
//    String r2 = KongUpstreams.builder().build().listAll();
//    JSONArray upstreams = JSON.parseObject(r2).getJSONArray("data");
//    for (int i = 0; i < upstreams.size(); i++) {
//      String host = upstreams.getJSONObject(i).getString("name");
//      String r3 = KongTargets.builder().upstreamName(host).build().listByUpstreamName();
//      JSONObject targets = JSON.parseObject(r3);
//      JSONArray targetsList = targets.getJSONArray("data");
//      for (int k = 0; k < targetsList.size(); k++) {
//        KongTargets.builder()
//            .id(targetsList.getJSONObject(k).getString("id"))
//            .build()
//            .delete(false);
//      }
//      KongUpstreams.builder().name(host).build().delete(false);
//    }
//  }
//
//  public void deleteAllPlugins() {
//    String r2 = KongPlugins.builder().build().listAll();
//    JSONArray plugins = JSON.parseObject(r2).getJSONArray("data");
//    for (int i = 0; i < plugins.size(); i++) {
//      KongPlugins.builder().id(plugins.getJSONObject(i).getString("id")).build().delete(false);
//    }
//  }
//
//  public void deleteAllConsumers() {
//    String r2 = KongConsumers.builder().build().listAll();
//    JSONArray plugins = JSON.parseObject(r2).getJSONArray("data");
//    for (int i = 0; i < plugins.size(); i++) {
//      KongConsumers.builder().id(plugins.getJSONObject(i).getString("id")).build().delete(false);
//    }
//  }
//
////  public static void main(String[] args) {
////    final BatchDeleteKong batchDeleteKong = new BatchDeleteKong();
////    batchDeleteKong.init();
////
////    new Thread(
////            new Runnable() {
////              @Override
////              public void run() {
////                try {
////                  batchDeleteKong.deleteAllPlugins();
////                } catch (Exception e) {
////                  e.printStackTrace();
////                }
////              }
////            })
////        .start();
////
////    new Thread(
////            new Runnable() {
////              @Override
////              public void run() {
////                for (int i = 0; i < 15; i++) {
////                  try {
////                    batchDeleteKong.deleteAllKong();
////                  } catch (Exception e) {
////                    e.printStackTrace();
////                  }
////                }
////              }
////            })
////        .start();
////
////    new Thread(
////            new Runnable() {
////              @Override
////              public void run() {
////                for (int i = 0; i < 15; i++) {
////                  batchDeleteKong.deleteAllUpstream();
////                }
////              }
////            })
////        .start();
////
////
////    new Thread(
////            new Runnable() {
////              @Override
////              public void run() {
////                for (int i = 0; i < 15; i++) {
////                  batchDeleteKong.deleteAllConsumers();
////                }
////              }
////            })
////        .start();
////  }
//
//
//}
