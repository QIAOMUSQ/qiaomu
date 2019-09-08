package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.modules.sys.entity.SysFileEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-01-15 18:45
 */
public interface SysFileService extends IService<SysFileEntity> {
    void insertInfo(SysFileEntity fileEntity);

    Map<String,String> imageUrls(HttpServletRequest request);

    /**
     * 根据url删除文件
     * @param url
     * @return
     */
    boolean deleteFileByHttpUrl(String url);
}
