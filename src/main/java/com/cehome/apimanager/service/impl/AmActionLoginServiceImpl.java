package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionLoginDao;
import com.cehome.apimanager.model.dto.AmActionLoginQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionLoginReqDto;
import com.cehome.apimanager.model.po.AmActionLogin;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmActionLoginService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmActionLoginServiceImpl implements IAmActionLoginService {

    @Autowired
    private AmActionLoginDao actionLoginDao;

    @Autowired
    private IAmDomainService domainService;

    public List<AmActionLogin> list(AmActionLoginQueryReqDto dto) {
        return actionLoginDao.list(dto);
    }

    public void setCookieStore() {
        AmActionLoginQueryReqDto dto = new AmActionLoginQueryReqDto();
        List<AmActionLogin> actionLoginList = actionLoginDao.list(dto);
        if (actionLoginList == null || actionLoginList.isEmpty()) {
            return;
        }
        HttpUtils httpUtils = HttpUtils.getInstance();
        httpUtils.clearCookieStore();
        for (AmActionLogin actionLogin : actionLoginList) {
            Integer domainId = actionLogin.getDomainId();
            AmDomain domain = domainService.findById(domainId);
            httpUtils.loginForCookie(domain.getDomainName(), actionLogin.getRequestUrl(), actionLogin.getRequestType(), actionLogin.getAccountParam());
        }
    }

    @Override
    public void delete(AmActionLoginReqDto dto) {
        actionLoginDao.delete(dto.getId());
    }

    @Override
    public AmActionLogin findById(Integer id) {
        return actionLoginDao.get(id);
    }

    @Override
    public Page<AmActionLogin> findPage(AmActionLoginQueryReqDto dto) {
        return actionLoginDao.find(dto);
    }

    @Override
    public void update(AmActionLoginReqDto dto) {
        actionLoginDao.update(dto);
    }

    @Override
    public void add(AmActionLoginReqDto dto) {
        actionLoginDao.add(dto);
    }
}
