package com.qiaomu.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.sys.entity.AppErrorLog;
import com.qiaomu.modules.sys.entity.VO.AppErrorLogVO;
import com.qiaomu.modules.sys.service.AppErrorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
