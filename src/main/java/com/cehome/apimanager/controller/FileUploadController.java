package com.cehome.apimanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cehome.apimanager.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/file")
public class FileUploadController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(@RequestParam("javaFile") MultipartFile javaFile) {
        try {
            InputStream inputStream = javaFile.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Object o = ois.readObject();
            String json = JSON.toJSONString(o, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(json);
            return toSuccess(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
