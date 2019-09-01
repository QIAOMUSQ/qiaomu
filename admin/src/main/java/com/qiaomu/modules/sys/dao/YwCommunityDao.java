package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.sys.entity.YwCommunity;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-19 22:56
 */
public interface YwCommunityDao extends BaseMapper<YwCommunity> {
    YwCommunity queryById(Long communityId);

    List<YwCommunity>findAllByCondition(YwCommunity condition);


    List<YwCommunity> selectPageByCondition(Page<YwCommunity> page, YwCommunity condition);

    void setCommunityDisEnable(Map<String,Object> map);

    List<YwCommunity> getDeleteCommunity(YwCommunity community);

}
