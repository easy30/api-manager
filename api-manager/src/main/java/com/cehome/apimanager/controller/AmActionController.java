package com.cehome.apimanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.cehome.apimanager.common.BaseController;
import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.model.dto.AmActionQueryReqDto;
import com.cehome.apimanager.model.dto.AmActionReqDto;
import com.cehome.apimanager.model.dto.AmActionResDto;
import com.cehome.apimanager.model.po.AmAction;
import com.cehome.apimanager.model.po.AmUser;
import com.cehome.apimanager.service.IAmActionService;
import com.cehome.apimanager.utils.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apimanager/action")
public class AmActionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AmActionController.class);

    @Autowired
    private IAmActionService actionService;

    /**
     * 添加接口文档
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Map<String, Object> add(HttpSession session, @RequestBody AmActionReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setCreateUser(loginUser.getId());
            actionService.add(dto);
            return toSuccess(dto.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e);
        }
    }

    /**
     * 更新接口文档
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Map<String, Object> update(HttpSession session, @RequestBody AmActionReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setUpdateUser(loginUser.getId());
            actionService.update(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e);
        }
    }

    /**
     * 根据主键返回接口文档
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Map<String, Object> findById(Integer id) {
        try {
            AmActionResDto amActionResDto = actionService.findById(id);
            return toSuccess(amActionResDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 根据id删除接口文档
     *
     * @param dto
     * @return
     */
    @RequestMapping("delete")
    public Map<String, Object> delete(HttpSession session, AmActionReqDto dto) {
        try {
            AmUser loginUser = WebUtils.getLoginUser(session);
            dto.setUpdateUser(loginUser.getId());
            actionService.delete(dto);
            return toSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 分页查询接口文档列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("findPage")
    public Map<String, Object> findPage(AmActionQueryReqDto dto) {
        try {
            Page<AmAction> page = actionService.findPage(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }
    @RequestMapping("search")
    public Map<String, Object> search(AmActionQueryReqDto dto) {
        try {
            Page<AmActionResDto> page = actionService.search(dto);
            return toSuccess(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 获取接口文档列表
     *
     * @param dto
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> list(AmActionQueryReqDto dto) {
        try {
            List<AmAction> list = actionService.list(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 查询每个项目下的接口数量
     *
     * @param dto
     * @return
     */
    @RequestMapping("countGroupByProject")
    public Map<String, Object> countGroupByProject(AmActionQueryReqDto dto) {
        try {
            List<Map<String, Integer>> list = actionService.countGroupByProject(dto);
            return toSuccess(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return toFail(e.getMessage());
        }
    }

    /**
     * 导出接口文档
     *
     * @param actionId 接口编号
     * @param request
     * @param response
     */
    @RequestMapping("downloadPdf")
    public void downloadPdf(Integer actionId, HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject result = actionService.createActionPdf(actionId);
            File file = new File(result.getString("filePath"));
            if (file.exists()) {
                String userAgent = request.getHeader("User-Agent");
                String formFileName = result.getString("fileName") + "接口说明文档.pdf";
                // 针对IE或者以IE为内核的浏览器：
                if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                    formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
                } else {
                    // 非IE浏览器的处理：
                    formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + formFileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
