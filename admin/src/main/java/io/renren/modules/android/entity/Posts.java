package io.renren.modules.android.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import io.renren.modules.sys.entity.SysUserEntity;


import java.io.Serializable;
import java.util.Date;

/**
 * @Author wonly
 * Create By 2019/1/1
 *
 * 帖子
 */

@TableName("quark_posts")
public class Posts implements Serializable {

    private Integer id;

    //与标签的关系
    private Label label;

    //标题
    private String title;

    //内容
    private String content;

    //发布时间
    private Date initTime;

    //是否置顶
    private boolean top;

    //是否精华
    private boolean good;


    private SysUserEntity user;


    //回复数量

    private int replyCount = 0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
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

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public SysUserEntity getUser() {
        return user;
    }

    public void setUser(SysUserEntity user) {
        this.user = user;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", label=" + label +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", initTime=" + initTime +
                ", top=" + top +
                ", good=" + good +
                ", user=" + user +
                ", replyCount=" + replyCount +
                '}';
    }
}
