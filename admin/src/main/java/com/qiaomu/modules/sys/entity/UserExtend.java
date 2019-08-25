package com.qiaomu.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-17 7:08
 */
@TableName("yw_user_extend")
public class UserExtend implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;
    private Long userId;
   // private String userIdentity;
    private String address;
    //private Long imgId;
   // private String nickName;
    private String realName;
    @TableField(exist = false)
    private Long communityId;
    private Long companyId;
    private Date createTime;
    private Date checkTime;
    private Long checkUserId;
    private String check;   //是否审核 0:待审核 1：通过 2：不通过 3：禁用
    private String companyRoleType; //用户权限：5：游客  4：业主  3:物业工作人员 2:物业管理员
   // private String sex;
    private String info;

    private boolean status;//状态

    @TableField(exist = false)
    private String communityName;
    @TableField(exist = false)
    private String userPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyRoleType() {
        return companyRoleType;
    }

    public void setCompanyRoleType(String companyRoleType) {
        this.companyRoleType = companyRoleType;
    }

    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
