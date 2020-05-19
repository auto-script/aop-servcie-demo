package cn.mlick.aopservicedemo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/file")
    @ResponseBody
    public Map<String, Object> upload(MultipartHttpServletRequest multipartRequest,
                                      HttpServletResponse response) throws IOException {
        //获取前台传值
        multipartRequest.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Map<String, Object> map = TestCtrl.putAllHeaders(multipartRequest);


        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        if (CollectionUtils.isEmpty(fileMap)) {
            map.put("upload-status", "文件流为空");
            return map;
        }


        String ctxPath = "/data/file/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        ctxPath += ymd + "/";
        //创建文件夹
        File file = new File(ctxPath);
        if (!file.exists() && file.mkdirs()) {
            System.out.println("创建文件夹成功:" + ctxPath);
        }

        String path = ctxPath;
        String responseStr;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件名
            MultipartFile mf = entity.getValue();
            String fileName = mf.getOriginalFilename();
            byte[] bytes = mf.getBytes();

            if (fileName == null || "".equals(fileName) || bytes.length == 0) {
                continue;
            }

            String strEnc = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // 构成新文件名
            String newFileName = strEnc + "-" + fileName;

            File uploadFile = new File(ctxPath + newFileName);

            try {
                FileCopyUtils.copy(bytes, uploadFile);
                path = ctxPath + newFileName;
                responseStr = "上传成功";
            } catch (IOException e) {
                responseStr = "上传失败";
                e.printStackTrace();
            }

            map.put("upload-path-" + mf.getName(), path);
            map.put("upload-status-" + mf.getName(), responseStr);
        }

        System.out.println(map);
        return map;
    }


    @RequestMapping("/file/download")
    @ResponseBody
    public ResponseEntity<byte[]> uploadAndDownload(@RequestBody(required = false) Map<String, String> map1,
                                                    MultipartHttpServletRequest multipartRequest,
                                                    HttpServletResponse response) throws IOException {
        //获取前台传值
        multipartRequest.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Map<String, Object> map = TestCtrl.putAllHeaders(multipartRequest, null, map1);


        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        if (CollectionUtils.isEmpty(fileMap)) {
            map.put("upload-status", "文件流为空");
            return null;
        }

        String ctxPath = "/data/file/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        ctxPath += ymd + "/";
        //创建文件夹
        File file = new File(ctxPath);
        if (!file.exists() && file.mkdirs()) {
            System.out.println("创建文件夹成功:" + ctxPath);
        }

        String path = ctxPath;
        String responseStr;
        File uploadFile = null;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件名
            MultipartFile mf = entity.getValue();
            String fileName = mf.getOriginalFilename();
            byte[] bytes = mf.getBytes();

            if (fileName == null || "".equals(fileName) || bytes.length == 0) {
                continue;
            }

            String strEnc = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // 构成新文件名
            String newFileName = strEnc + "-" + fileName;

            uploadFile = new File(ctxPath + newFileName);

            try {
                FileCopyUtils.copy(bytes, uploadFile);
                path = ctxPath + newFileName;
                responseStr = "上传成功";
            } catch (IOException e) {
                responseStr = "上传失败";
                e.printStackTrace();
            }

            map.put("upload-path-" + mf.getName(), path);
            map.put("upload-status-" + mf.getName(), responseStr);
        }

        if (uploadFile == null) {
            map.put("upload-file", "文件流上传失败");
            return null;
        }

        byte[] body;
        InputStream is = new FileInputStream(uploadFile);
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