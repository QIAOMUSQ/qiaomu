package com.qiaomu.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.modules.sys.entity.SysFileEntity;
import com.qiaomu.modules.sys.service.SysFileService;
import jodd.io.FileUtil;
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
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    /**
     * 文件上传
     *
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "upload")
    public void upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");//application/html
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

       // FileInfo fileInfo = new FileInfo();
        SysFileEntity fileEntity = new SysFileEntity();
        if (!file.isEmpty()) {
            try {
                String orgName = file.getOriginalFilename();
                Long fileSize = file.getSize();

                String saveName = UUID.randomUUID().toString() + "." + orgName.substring(orgName.lastIndexOf(".") + 1, orgName.length());

                File dir = new File(savePath);
                if (!dir.exists())
                    dir.mkdirs();

                File newFile = new File(savePath + saveName);
                fileEntity.setDateTime(new Date());
                FileUtil.writeStream(newFile, file.getInputStream());
                fileEntity.setName(saveName);
                fileEntity.setPath(savePath + saveName);
                fileEntity.setOrgName(orgName);
                //fileInfo.setRealFileName(saveName);
                fileEntity.setFileSize(fileSize);
              //  fileInfo.setFlag("1");
               // fileInfo.setFileNotes(orgName.substring(0, orgName.lastIndexOf(".")));
                sysFileService.insert(fileEntity);
                String result = JSON.toJSON(fileEntity).toString();
                out.println(result);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                //fileInfo.setFlag("0");
                String result = JSON.toJSON(fileEntity).toString();
                out.print(result);
                out.close();
            }
        }

        return;
    }

}
