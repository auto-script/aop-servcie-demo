package cn.mlick.aopservicedemo.controller;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author lixiangxin
 * @date 2019/3/29 10:05
 **/

@RestController
public class TestMainCtrl {

  @ApiIgnore
  @RequestMapping("/")
  public String t1(HttpServletRequest httpServletRequest) {
    String serverName = httpServletRequest.getServerName();
    return "Welcome " + Optional.ofNullable(serverName).map(s -> s.split("\\.")[0]).orElse("XiangXin Li") + " --> Test Api Interface";
  }

}
