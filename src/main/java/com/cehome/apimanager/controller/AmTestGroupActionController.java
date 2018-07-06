package com.cehome.apimanager.controller;

import com.cehome.apimanager.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apimanager/groupaction")
public class AmTestGroupActionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmTestGroupActionController.class);

}
