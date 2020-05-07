package cn.mlick.aopservicedemo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        System.out.println(TestCtrl.putAllHeaders(request));

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
}
