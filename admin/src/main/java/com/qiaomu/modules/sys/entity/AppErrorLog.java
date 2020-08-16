package com.qiaomu.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2020-08-16 17:43
 */
@Data
@TableName("app_error_log")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="APP错误日志")
public class AppErrorLog {
    @TableId
    private Long id;
    @ApiModelProperty(value = "app版本")
    private String appVersion;
    @ApiModelProperty(value = "客户端类型")
    private String clientType;
    @ApiModelProperty(value = "用户名称")
    private String loginName;
    @ApiModelProperty(value = "时间")
    private Date timeStamp;
    @ApiModelProperty(value = "报错页面")
    private String page;
    @ApiModelProperty(value = "exception | requestError |fail   分别代表手机端抛异常、请求服务端异常、用户操作失败")
    private String eventType;

    @TableField(exist = false)
    private List<AppErrorEventLog> eventLogList;
}
