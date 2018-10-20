package io.renren.modules.info_management.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:新闻消息实体
 * @Date 2018-10-15 21:54
 */
@TableName("yw_news_info")
public class NewsInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long userId;//用户id

    private String infoType;//消息类型： 01：政策公告 02：家事讨论  03：七彩生活  04：闲置分享  05：兴趣交流  06：理财专区

    private String imgUrl;//图片ID

    private String title;//消息标题

    private String content;//消息内容

    private Date time;

    private Date modifyTime;

    private int applaud;//新闻点赞同数

    private int oppose;//新闻踩数

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

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getApplaud() {
        return applaud;
    }

    public void setApplaud(int applaud) {
        this.applaud = applaud;
    }

    public int getOppose() {
        return oppose;
    }

    public void setOppose(int oppose) {
        this.oppose = oppose;
    }
}
