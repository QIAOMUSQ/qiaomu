package com.qiaomu.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.sys.entity.SysFileEntity;
import com.qiaomu.modules.sys.service.SysFileService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李品先
 * @description:文件、图片操作控制器
 * @Date 2019-01-15 18:58
 */
@Controller
@RequestMapping("mobile/sysFile")
public class SysFileConcroller {

    @Autowired
    private SysFileService sysFileService;

    @Value("${file.save}")
    private String savePath;


    @ResponseBody
    @RequestMapping(value = "upFile", method = RequestMethod.POST)
    public void uploadOrderSignImage(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        try {
            MultipartHttpServletRequest rq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> file_list = rq.getFileMap();
            System.out.println("file_list = [" + JSON.toJSON(file_list) + "]");
            File dir = new File(savePath);
            if (!dir.exists()) dir.mkdirs();

            SysFileEntity fileEntity = new SysFileEntity();
            String filePath = "";
            if (file_list != null && file_list.size() > 0) {
                if (file_list.containsKey("inputName")) {
                    MultipartFile file = file_list.get("inputName");
                    if (file != null) {
                        // 保存图片
                        String fileName = file.getOriginalFilename();
                        String newFileName = "";
                        String uploadPath = "";
                        String[] desp = fileName.split("\\.");
                        if (desp != null && desp.length > 0) {
                            String extendName = desp[desp.length - 1];
                            newFileName = desp[0] + "_" + new Date().getTime() + "." + extendName;
                            uploadPath = savePath + "upload\\";
                            File saveFile = new File(uploadPath, newFileName);
                            file.transferTo(saveFile);
                            filePath = uploadPath.substring(0, uploadPath.length() - 1) + "/" + newFileName;

                            fileEntity.setName(newFileName);
                            fileEntity.setPath(filePath);
                            fileEntity.setDateTime(new Date());
                            sysFileService.insert(fileEntity);

                        }
                        session.setAttribute("filPath", filePath);
                        System.out.println("request = [" + request + "], filePath = [" + filePath + "], fileEntity = [" + JSON.toJSON(fileEntity) + "]");
                        response.getWriter().print(fileEntity.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
