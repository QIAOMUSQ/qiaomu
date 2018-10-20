package io.renren.modules.info_management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.info_management.entity.NewsInfoEntity;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2018-10-15 22:26
 */
public interface NewsInfoDao extends BaseMapper<NewsInfoEntity> {

    //List<Long> queryDetpIdList(Long parentId);

    List<NewsInfoEntity> queryNewsInfoOrderByTimeDesc();
}
