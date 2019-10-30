package com.qiaomu.modules.workflow.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 李品先
 * @description:报修信息
 * @Date 2019-10-06 14:13
 */
@TableName("yw_repairs_info")
public class RepairsInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long userId;

    private Long communityId;   //社区id

    private String userOpinion; //用户评价

    private String detail;  //事务描述细节

    private Date createTime;    //发起时间

    private Date repairsTime; //维修时间

    private String location;    //事发位置

    private String picture; //图片存放地址

    private String serviceDate;//上门维修时间

    private String repairsType;// 维修类型 0：电力 1：供水 2：煤气 3房屋

    private String status;  //状态  0：已提交 1：物业已分派人员 2：处理完成

    private String starType;//星级评价 1:一星 2：二星 3：三星 4：四星  5：五星

    private Long repairsId;//维修人员ID

    private Date apportionTime;//分配时间

    private String lingerTime;

    @TableField(exist = false)
    private String repairsPhone;  //  处理人号码

    @TableField(exist = false)
    private String repairsName;  // 维修人名

    @TableField(exist = false)
    private String userRealName;  //  用户人真实名称

    @TableField(exist = false)
    private String userPhone;  //  用户号码

    @TableField(exist = false)
    private Long imgId;

    @TableField(exist = false)
    private List<Long> communityIds;

    @TableField(exist = false)
    private String communityName;

    @TableField(exist = false)
    private List<Long> ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getUserOpinion() {
        return userOpinion;
    }

    public void setUserOpinion(String userOpinion) {
        this.userOpinion = userOpinion;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getRepairsType() {
        return repairsType;
    }

    public void setRepairsType(String repairsType) {
        this.repairsType = repairsType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public Long getRepairsId() {
        return repairsId;
    }

    public void setRepairsId(Long repairsId) {
        this.repairsId = repairsId;
    }

    public String getRepairsPhone() {
        return repairsPhone;
    }

    public void setRepairsPhone(String repairsPhone) {
        this.repairsPhone = repairsPhone;
    }

    public String getRepairsName() {
        return repairsName;
    }

    public void setRepairsName(String repairsName) {
        this.repairsName = repairsName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public Date getRepairsTime() {
        return repairsTime;
    }

    public void setRepairsTime(Date repairsTime) {
        this.repairsTime = repairsTime;
    }

    public Date getApportionTime() {
        return apportionTime;
    }

    public void setApportionTime(Date apportionTime) {
        this.apportionTime = apportionTime;
    }

    public List<Long> getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(List<Long> communityIds) {
        this.communityIds = communityIds;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getLingerTime() {
        return lingerTime;
    }

    public void setLingerTime(String lingerTime) {
        this.lingerTime = lingerTime;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
