package com.qiaomu.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.CommonUtils;
import com.qiaomu.modules.sys.dao.SysFileDao;
import com.qiaomu.modules.sys.entity.SysFileEntity;
import com.qiaomu.modules.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.qiaomu.common.utils.Constant.SERVER_URL;

/**
 * @author 李品先
 * @description:
 * @Date 2019-01-15 18:46
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {



    @Value("${file.save}")
    private String savePath;

    @Override
    public void insertInfo(SysFileEntity fileEntity) {
         this.baseMapper.insertInfo(fileEntity);
    }

    @Override
    public Map<String, String> imageUrls(HttpServletRequest request) {
        Map<String,String> imageUrls = new HashMap<String,String>();
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        Iterator<String> filenames=req.getFileNames();

        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();
        while(filenames.hasNext()){
            String filekey = filenames.next();
            MultipartFile multipartFile = req.getFile(filekey);
            String fileName = CommonUtils.mkFileName(multipartFile.getOriginalFilename()) ;
            File saveFile = new File(savePath+fileName);
            try {
                multipartFile.transferTo(saveFile);
                imageUrls.put(filekey,SERVER_URL+"/outapp/image/"+fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return imageUrls;
    }
}
