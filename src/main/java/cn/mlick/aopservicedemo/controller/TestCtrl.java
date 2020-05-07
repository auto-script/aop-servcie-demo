package cn.mlick.aopservicedemo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiangxin
 * @date 2019/3/29 10:05
 **/

@RestController
@RequestMapping("/test")
public class TestCtrl {


    @RequestMapping("/timeout/{timeout}")
    public void timeout(@PathVariable int timeout, String name, Integer age,
                        HttpServletRequest httpServletRequest) throws InterruptedException {
        Thread.currentThread().join(timeout * 1000L);

        System.out.println("超时了" + timeout + "s name=>" + name + " age=>" + age);
    }


    @RequestMapping("/test1")
    public Object t1(String name, Integer age, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("response", "Your name is " + name + ",age is " + age);
        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @GetMapping("/testParams")
    public Object t2(String name, Integer age, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("response", "Your name is " + name + ",age is " + age);
        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @GetMapping("/testParams2")
    public Object t22(String name2, Integer age2, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("response", "Your name is " + name2 + ",age is " + age2);
        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @PostMapping("/testRequestBody")
    public Map<String, Object> t3(@RequestBody(required = false) Map<String, String> map1,
                                  HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("response", map1);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }

    @PostMapping("/testRequestBodyByHeader")
    public Map<String, Object> t4(HttpServletRequest httpServletRequest,
                                  @RequestBody(required = false) Map<String, String> map1) {
        Map<String, Object> map = new HashMap<>(2);

        putAllHeaders(httpServletRequest, map, map1);

        return map;
    }


    @PostMapping("/testRequestBodyByQuery")
    public Map<String, Object> t5(String name, Integer age, @RequestBody(required = false) Map<String, String> map1,
                                  HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("query", "Your name is " + name + ",age is " + age);
        map.put("response", map1);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }

    @PostMapping("/testRequestBody/{name}/{age}")
    public Map<String, Object> t5_2(@PathVariable String name, @PathVariable Integer age,
                                    @RequestBody(required = false) Map<String, String> map1) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("path", "Your name is " + name + ",age is " + age);
        map.put("response", map1);
        return map;
    }

    @GetMapping("/get/{a}/{b}")
    public Map<String, Object> t8(@PathVariable String a, @PathVariable Integer b,
                                  HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("value", "test-1-version" + a + b);
        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @GetMapping("/get/{a}/{b}/{c}")
    public Map<String, Object> t8_2(@PathVariable String a, @PathVariable Integer b, @PathVariable Integer c,
                                    HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("value", "test-1-version" + a + b + c);
        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @GetMapping("/{age}/delete")
    public Map<String, Object> t9(String name, @PathVariable Integer age, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("value", "test-" + age + "-version");
        putAllHeaders(httpServletRequest, map);
        return map;
    }


    @PutMapping("/getPut")
    public Map<String, Object> t10(@RequestBody(required = false) Map<String, String> map1, String name,
                                   HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }

    @PutMapping("/getPut2")
    public Map<String, Object> t10(@RequestBody(required = false) Map<String, String> map1, String name, Integer age,
                                   HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }

    @PutMapping("/gPut/{name}")
    public Map<String, Object> t11(@RequestBody(required = false) Map<String, String> map1, @PathVariable String name,
                                   HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", name);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }

    @PutMapping("/gPut2/{name}/{age}")
    public Map<String, Object> t11_2(@RequestBody(required = false) Map<String, String> map1, @PathVariable String name,
                                     @PathVariable Integer age, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", name);
        map.put("age", age);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }

    @PutMapping("/gPut3")
    public Map<String, Object> t11_3(@RequestBody(required = false) Map<String, String> map1, String name, Integer age,
                                     HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", name);
        map.put("age", age);
        putAllHeaders(httpServletRequest, map, map1);
        return map;
    }


    @DeleteMapping("/gDelete/{name}")
    public Map<String, Object> t12(@PathVariable String name, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        Map<String, Object> map1 = new HashMap<>(1);
        map1.put("name", name);
        map.put("path", map1);

        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @DeleteMapping("/gDelete2")
    public Map<String, Object> t13(String name, String sex, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", name);
        map.put("sex", sex);
        putAllHeaders(httpServletRequest, map);
        return map;
    }

    @PostMapping(value = "/formData", produces = {"application/json;charset=UTF-8"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> formData(String name, String pwd, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", name);
        map.put("pwd", pwd);
        putAllHeaders(httpServletRequest, map);
        return map;
    }


    @PostMapping(value = "/formUrlencoded", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> formUrlencoded(String emp, String epg, HttpServletRequest httpServletRequest) {
        Map<String, Object> map1 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(2);
        map.put("emp", emp);
        map.put("epg", epg);
        map1.put("form", map);
        putAllHeaders(httpServletRequest, map1);
        return map1;
    }


    @PostMapping(value = "/formUrlencoded2/{name}/{age}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> formUrlencoded2(@PathVariable String name, @PathVariable Integer age, String emp,
                                               String epg, HttpServletRequest httpServletRequest) {
        Map<String, Object> map1 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(2);
        map.put("emp", emp);
        map.put("epg", epg);

        Map<String, Object> map2 = new HashMap<>(2);
        map.put("name", name);
        map.put("age", age);

        map1.put("form", map);
        map1.put("path", map2);

        putAllHeaders(httpServletRequest, map1);
        return map1;
    }

    private void putAllHeaders(HttpServletRequest httpServletRequest, Map<String, Object> map) {
        putAllHeaders(httpServletRequest, map, "");
    }

    private void putAllHeaders(HttpServletRequest httpServletRequest, Map<String, Object> map, Object requestBody) {
        Map<String, Object> headers = new HashMap<>(8);
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, httpServletRequest.getHeader(key));
        }
        map.put("headers", headers);

        Map<String, Object> parameters = new HashMap<>(2);
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            parameters.put(key, httpServletRequest.getParameter(key));
        }
        map.put("parameters", parameters);

        map.put("body", requestBody);
    }


}
