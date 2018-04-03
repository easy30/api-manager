package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.MetaKv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/meta")
public class CommonMetaController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CommonMetaController.class);

    @RequestMapping("findMeta")
    public Map<String, Object> findMeta(String metaId) {
        try {
            List<MetaKv> KVS = new ArrayList<>();
            switch (metaId) {
                case "1":
                    KVS = CommonMeta.FieldType.KVS;
                    break;
                case "2":
                    KVS = CommonMeta.RequestType.KVS;
                    break;
                case "3":
                    KVS = CommonMeta.Status.KVS;
                    break;
            }
            return toSuccess(KVS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
}
