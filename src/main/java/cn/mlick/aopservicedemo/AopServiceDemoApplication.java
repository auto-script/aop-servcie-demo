package cn.mlick.aopservicedemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class AopServiceDemoApplication implements ApplicationRunner, ApplicationListener<WebServerInitializedEvent> {


    @Value("${server.servlet.context-path}")
    private String rootPath;

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(AopServiceDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("启动成功====1....................");

        String osName = System.getProperty("os.name");
        if (!osName.contains("Windows")) {
            return;
        }

        String url = "http://" + IPUtils.getWlanIp() + ":" + port + rootPath;

        System.out.println(url);

        try {
            Runtime.getRuntime().exec("cmd   /c   start   " + url);
        } catch (IOException ignored) {
        }

        try {
            Runtime.getRuntime().exec("cmd   /c   start   " + url + "swagger-ui.html");
        } catch (IOException ignored) {
        }

    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        System.out.println("启动成功====2....................");
        WebServer webServer = webServerInitializedEvent.getWebServer();
        System.out.println(webServer.getPort());

    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("800MB"));
        factory.setMaxRequestSize(DataSize.parse("800MB"));
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }


}
