package com.qiaomu.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.HttpDownload;
import com.qiaomu.common.utils.ZipUtil;
import com.qiaomu.modules.sys.entity.AppErrorLog;
import com.qiaomu.modules.sys.entity.VO.AppErrorLogVO;
import com.qiaomu.modules.sys.service.AppErrorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @author 李品先
 * @description:app_error_log
 * @Date 2020-08-16 17:24
 */
@RestController
@Api(tags ="APP日志信息上传")
@RequestMapping(value = "mobile/appErrorLog")
public class AppErrorLogController {

    @Autowired
    private AppErrorLogService errorLogService;
    @Value("${error-file}")
    private String errorFile;

    @PostMapping("saveErrorLog")
    @ApiOperation("新增")
    public Object saveErrorLog(@RequestBody AppErrorLog appErrorLog){
        errorLogService.save(appErrorLog);
        return BuildResponse.success();
    }
    @GetMapping("getErrorLog")
    @ApiOperation("获取Log信息")
    public Object getErrorLog(AppErrorLogVO appErrorLog){
        EntityWrapper<AppErrorLog> wrapper = new EntityWrapper();
        wrapper.eq(StringUtils.isNotBlank(appErrorLog.getAppVersion()),"app_version",appErrorLog.getAppVersion())
                .eq(StringUtils.isNotBlank(appErrorLog.getClientType()),"client_type",appErrorLog.getClientType())
                .eq(StringUtils.isNotBlank(appErrorLog.getLoginName()),"login_name",appErrorLog.getLoginName())
                .like(StringUtils.isNotBlank(appErrorLog.getTimeStamp()),"time_stamp",appErrorLog.getTimeStamp());
        List<AppErrorLog> logList = errorLogService.selectLogList(wrapper);
        return BuildResponse.success(logList);
    }

    @GetMapping("downFile")
    @ApiOperation("下载Log信息")
    public void downFile(HttpServletRequest request, HttpServletResponse response){

        List<File> logList = errorLogService.getFileList();
        String fileName = "-压缩文件.zip";
        File zip = new File(errorFile+"zip/");
        if (!zip.exists())zip.mkdir();
        File zipFile= new File(errorFile+"zip/"+fileName);
        ZipUtil.toZip(logList,zipFile);
        HttpDownload.download( request,response,errorFile+"zip/"+fileName,fileName);
    }

}
