package cn.mlick.aopservicedemo.controller;

import cn.mlick.aopservicedemo.redis.RedisStrategy;
import cn.mlick.aopservicedemo.redis.RedisUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author lixiangxin
 * @date 2019/3/29 10:05
 **/

@RestController
public class TestMainCtrl {

  @ApiIgnore
  @GetMapping("/")
  public String t1(HttpServletRequest httpServletRequest) {
    String serverName = httpServletRequest.getServerName();
    return "Welcome " + Optional.ofNullable(serverName).map(s -> s.split("\\.")[0]).orElse("XiangXin Li") + " --> Test Api Interface";
  }

  @ApiIgnore
  @RequestMapping("/{env}/{apiId}")
  public String t1(@PathVariable String env,@PathVariable Long apiId, HttpServletRequest httpServletRequest) throws IOException {
    Properties properties = new Properties();

    byte[] readAllBytes = Files.readAllBytes(Paths.get(env + ".properties"));

    ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(readAllBytes);
    properties.load(arrayInputStream);
    arrayInputStream.close();

    String host = properties.getProperty("redis.host");
    String port = properties.getProperty("redis.port");
    String nodes = properties.getProperty("redis.nodes");

    RedisStrategy redisStrategy = RedisUtils.getInstance(host, Integer.parseInt(port), 3000, nodes);
    String kongIds = redisStrategy.getCache("api:" + apiId);

    return kongIds;
  }

}
