package com.cehome.apimanager.job;

import com.cehome.apimanager.service.IAmActionLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CookieStoreJob implements Runnable{
    @Autowired
    private IAmActionLoginService actionLoginService;

    @Override
    public void run() {
        actionLoginService.setCookieStore();
    }
}
