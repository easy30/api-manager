package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.CommonMeta;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmActionLoginDao;
import com.cehome.apimanager.exception.BizValidationException;
import com.cehome.apimanager.model.dto.AmActionLoginQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionLoginReqDto;
import com.cehome.apimanager.model.dto.AmActionLoginResDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.po.AmActionLogin;
import com.cehome.apimanager.model.po.AmDomain;
import com.cehome.apimanager.service.IAmActionLoginService;
import com.cehome.apimanager.service.IAmDomainService;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.utils.CompareUtils;
import com.cehome.apimanager.utils.HttpUtils;
import com.cehome.apimanager.utils.ThreadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmActionLoginServiceImpl implements IAmActionLoginService {

    @Autowired
    private AmActionLoginDao actionLoginDao;

    @Autowired
    private IAmDomainService domainService;

    @Autowired
    private IAmOperateLogService operateLogService;

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
        AmActionLogin actionLogin = actionLoginDao.get(dto.getId());
        actionLoginDao.delete(dto.getId());
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION_LOGIN.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.DELETE.getCode());
                operateLogReqDto.setOperateDesc("删除认证接口【" + actionLogin.getRequestUrl() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public AmActionLogin findById(Integer id) {
        AmActionLogin actionLogin = actionLoginDao.get(id);
        AmDomain domain = domainService.findById(actionLogin.getDomainId());
        AmActionLoginResDto actionLoginResDto = new AmActionLoginResDto();
        BeanUtils.copyProperties(actionLogin, actionLoginResDto);
        actionLoginResDto.setDomainName(domain.getDomainName());
        return actionLoginResDto;
    }

    @Override
    public Page<AmActionLogin> findPage(AmActionLoginQueryReqDto dto) {
        return actionLoginDao.find(dto);
    }

    @Override
    public void update(AmActionLoginReqDto dto) {
        AmActionLogin actionLogin = actionLoginDao.get(dto.getId());
        actionLoginDao.update(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION_LOGIN.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.UPDATE.getCode());
                operateLogReqDto.setOperateDesc("修改认证接口【" + dto.getId() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                if (!actionLogin.equals(dto)) {
                    operateLogReqDto.setContentChange(CompareUtils.compareFieldDiff(actionLogin, dto));
                }
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void add(AmActionLoginReqDto dto) {
        actionLoginDao.add(dto);
        ThreadUtils.execute(new ThreadUtils.Task() {
            @Override
            public void invoke() {
                AmOperateLogReqDto operateLogReqDto = new AmOperateLogReqDto();
                operateLogReqDto.setModuleCode(CommonMeta.Module.ACTION_LOGIN.getCode());
                operateLogReqDto.setOperateType(CommonMeta.OperateType.ADD.getCode());
                operateLogReqDto.setOperateDesc("增加认证接口【" + dto.getRequestUrl() + "】");
                operateLogReqDto.setOperateUser(dto.getOperateUser());
                operateLogReqDto.setEntityId(dto.getId());
                operateLogService.add(operateLogReqDto);
            }
        });
    }

    @Override
    public void authenticate(AmActionLoginReqDto dto) {
        AmActionLoginResDto actionLogin = (AmActionLoginResDto) this.findById(dto.getId());
        if (actionLogin == null) {
            throw new BizValidationException("认证接口不存在，认证接口编号【" + dto.getId() + "】");
        }
        Integer requestType = actionLogin.getRequestType();
        String accountParam = actionLogin.getAccountParam();
        String requestUrl = actionLogin.getRequestUrl();
        String domainName = actionLogin.getDomainName();
        HttpUtils httpUtils = HttpUtils.getInstance();
        boolean result = httpUtils.loginForCookie(domainName, requestUrl, requestType, accountParam);
        if (!result) {
            throw new BizValidationException("认证失败！");
        }
    }
}
