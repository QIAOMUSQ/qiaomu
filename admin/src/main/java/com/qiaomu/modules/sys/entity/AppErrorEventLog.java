package com.qiaomu.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2020-08-16 17:47
 */
@Data
@TableName("app_error_event_log")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="APP错误事件日志")
public class AppErrorEventLog {

    @TableId
    private Long id;
    @ApiModelProperty(value = "事件编码")
    private String eventCode;
    @ApiModelProperty(value = "事件名称")
    private String eventName;
    @ApiModelProperty(value = "细节")
    private String detail;
    @ApiModelProperty(value = "接口异常需要此字段")
    private String requestParams;
    @ApiModelProperty(value = "服务端返回报文")
    private String response;
    @ApiModelProperty(value = "APP错误日志ID")
    private Long errorLogId;
    @ApiModelProperty(value = "时间")
    private Date dateTime;
}
