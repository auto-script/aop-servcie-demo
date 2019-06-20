package cn.mlick.aopservicedemo.controller;

import cn.mlick.aopservicedemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lixiangxin
 * @date 2019/5/27 14:28
 **/
public class TestAopCtrl {

  //  @Autowired
  private TestService testService;

  @RequestMapping("/1")
  @ResponseBody
  public String t1(String s) {

    return testService.get(s);
  }


}
