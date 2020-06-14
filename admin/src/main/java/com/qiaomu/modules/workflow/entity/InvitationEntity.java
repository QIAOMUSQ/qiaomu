package com.qiaomu.modules.workflow.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 李品先
 * @description；公告表
 * @Date 2019-04-22 21:47
 */
@Data
@TableName("yw_invitation")
public class InvitationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField
    private Long id;
    private Long communityId;
    private Date createTime;
    private Date updateTime;
    @Length(max=30, message="用户名长度必须0-30之间")
    private String title;
    private String content;
    private String imgJson;
    @TableField(exist = false)
    private String communityName;
    @TableField(exist = false)
    private Long companyId;
    @TableField(exist = false)
    private Long userId;
    @TableField(exist = false)
    private String contentHtml;

    private List<String>  communityIds;

}
