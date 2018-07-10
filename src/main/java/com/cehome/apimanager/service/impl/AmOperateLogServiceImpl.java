package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmOperateLogDao;
import com.cehome.apimanager.model.dto.AmOperateLogQueryReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogResDto;
import com.cehome.apimanager.model.po.AmOperateLog;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmOperateLogService;
import com.cehome.apimanager.service.IAmUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AmOperateLogServiceImpl implements IAmOperateLogService {
    @Autowired
    private AmOperateLogDao operateLogDao;

    @Autowired
    private IAmUserService userService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void add(AmOperateLogReqDto dto) {
        dto.setOperateTime(new Date());
        operateLogDao.add(dto);
    }

    @Override
    public void update(AmOperateLogReqDto dto) {
        operateLogDao.update(dto);
    }

    @Override
    public List<AmOperateLog> list(AmOperateLogQueryReqDto dto) {
        List<AmOperateLog> list = operateLogDao.list(dto);
        List<AmOperateLog> operateLogResDtoList = new ArrayList<>();
        if(list != null && !list.isEmpty()){
            for(AmOperateLog operateLog : list){
                AmOperateLogResDto operateLogResDto = new AmOperateLogResDto();
                BeanUtils.copyProperties(operateLog, operateLogResDto);
                operateLogResDto.setOperateTimeFormat(sdf.format(operateLog.getOperateTime()));
                operateLogResDtoList.add(operateLogResDto);
            }
        }
        return operateLogResDtoList;
    }

    @Override
    public Page<AmOperateLog> findPage(AmOperateLogQueryReqDto dto) {
        Page<AmOperateLog> amOperateLogPage = operateLogDao.find(dto);
        List<AmOperateLog> datas = amOperateLogPage.getDatas();
        if(datas != null && !datas.isEmpty()){
            List<AmOperateLog> operateLogResDtoList = new ArrayList<>();
            for(AmOperateLog operateLog : datas){
                AmOperateLogResDto operateLogResDto = new AmOperateLogResDto();
                BeanUtils.copyProperties(operateLog, operateLogResDto);
                operateLogResDto.setOperateTimeFormat(sdf.format(operateLog.getOperateTime()));
                operateLogResDtoList.add(operateLogResDto);
            }
            amOperateLogPage.setDatas(operateLogResDtoList);
        }
        return amOperateLogPage;
    }

    @Override
    public AmOperateLog findById(Integer id){
        AmOperateLog operateLog = operateLogDao.get(id);
        AmOperateLogResDto operateLogResDto = new AmOperateLogResDto();
        BeanUtils.copyProperties(operateLog, operateLogResDto);
        AmUser user = userService.findById(operateLog.getOperateUser());
        if(user != null){
            operateLogResDto.setUserName(user.getUserName());
        }
        operateLogResDto.setOperateTimeFormat(sdf.format(operateLog.getOperateTime()));
        return operateLogResDto;
    }
}
