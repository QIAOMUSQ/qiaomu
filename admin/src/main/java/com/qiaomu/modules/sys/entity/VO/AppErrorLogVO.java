package com.qiaomu.modules.sys.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2020-08-16 20:56
 */
@Data
public class AppErrorLogVO {
    @ApiModelProperty(value = "app版本")
    private String appVersion;
    @ApiModelProperty(value = "客户端类型")
    private String clientType;
    @ApiModelProperty(value = "用户名称")
    private String loginName;
    @ApiModelProperty(value = "时间")
    private String timeStamp;
}
