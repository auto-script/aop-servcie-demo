package cn.mlick.aopservicedemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

/**
 * @author lixiangxin
 * @date 2020/5/7 13:29
 **/

@Controller
@RequestMapping("/download")
public class DownloadController {

    @RequestMapping("/file/{date}/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String date,
                                           @PathVariable String fileName,
                                           HttpServletRequest request) throws IOException {

        File file = new File("/data/file/" + date + "/" + fileName);
        byte[] body;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        try {
            int read = is.read(body);
            System.out.println(read);
        } catch (IOException e) {
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

        try {
            is.close();
        } catch (IOException ignored) {
        }

        return entity;
    }

    @RequestMapping("/getFile")
    public ResponseEntity<byte[]> getFile(@RequestBody(required = false) Map<String, String> map1,
                                          HttpServletRequest request) throws IOException {

        Map<String, Object> stringObjectMap = TestCtrl.putAllHeaders(request, null, map1);
        Path path = Files
                .write(Paths.get("/data/file/" + UUID.randomUUID() + ".txt"), JSON.toJSONString(stringObjectMap, SerializerFeature.PrettyFormat)
                        .getBytes());
        File file = path.toFile();

        byte[] body;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        try {
            int read = is.read(body);
            System.out.println(read);
        } catch (IOException e) {
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

        try {
            is.close();
        } catch (IOException ignored) {
        }

        return entity;
    }


    @PostMapping(value = "/getFileForm", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<byte[]> getFileForm(String name, String nick,
                                              HttpServletRequest request) throws IOException {
        Map<String, Object> stringObjectMap = TestCtrl.putAllHeaders(request);
        stringObjectMap.put("name", name);
        stringObjectMap.put("nick", nick);

        Path path = Files
                .write(Paths.get("/data/file/" + UUID.randomUUID() + ".txt"), JSON.toJSONString(stringObjectMap, SerializerFeature.PrettyFormat)
                        .getBytes());
        File file = path.toFile();

        byte[] body;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        try {
            int read = is.read(body);
            System.out.println(read);
        } catch (IOException e) {
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

        try {
            is.close();
        } catch (IOException ignored) {
        }

        return entity;
    }
}
