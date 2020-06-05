package cn.mlick.aopservicedemo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lixiangxin
 * @date 2020/6/4 14:24
 **/
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    //实现拦截器 要拦截的路径以及不拦截的路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径

        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**")
                .excludePathPatterns("/loginPage", "/login");
    }
}

