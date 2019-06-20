package cn.mlick.aopservicedemo.service;

import cn.mlick.aopservicedemo.annotation.MarsValid;
import org.springframework.stereotype.Service;

/**
 * @author lixiangxin
 * @date 2019/3/29 10:06
 **/

@Service
public class TestService {



    @MarsValid("isNotEmpty(#s)")
    public String get(String s) {

        return get2(s);
    }

    public String get2(String s) {

        return "lxx-" + s;
    }
}
