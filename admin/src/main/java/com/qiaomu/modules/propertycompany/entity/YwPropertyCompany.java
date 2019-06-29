package com.qiaomu.modules.propertycompany.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:18
 */
@TableName("yw_property_company")
public class YwPropertyCompany
        implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String name;
    private String address;
    private String legalPerson;
    private String telPhone;
    private String companyImg;
    private String usageType;
    private Date createTime;
    private Long administratorId;
    private String adminPhone;

    @TableField(exist = false)
    private String administratorName;

    public String getAdministratorName() {
        return this.administratorName;
    }

    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName;
    }

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalPerson() {
        return this.legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getTelPhone() {
        return this.telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getCompanyImg() {
        return this.companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public String getUsageType() {
        return this.usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }



    public String getAdminPhone() {
        return this.adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}