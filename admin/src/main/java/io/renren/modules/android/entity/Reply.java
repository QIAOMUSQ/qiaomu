package io.renren.modules.android.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.renren.modules.sys.entity.SysUserEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author LHR
 * Create By 2017/8/18
 *
 * 回复
 */
@TableName("quark_reply")
public class Reply implements Serializable {

    private Integer id;

    //回复的内容
    private String content;

    //回复时间
    private Date initTime;

    //点赞个数
    private Integer up = 0;

    //与话题的关联关系
    private Posts posts;

    //与用户的关联关系
    private SysUserEntity user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public SysUserEntity getUser() {
        return user;
    }

    public void setUser(SysUserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", initTime=" + initTime +
                ", up=" + up +
                ", user=" + user +
                '}';
    }
}
