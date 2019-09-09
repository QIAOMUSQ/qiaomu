package com.qiaomu.modules.infopublish.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.infopublish.entity.InvitationEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-22 22:46
 */
public interface InvitationDao extends BaseMapper<InvitationEntity> {

    List<InvitationEntity> selectPageAll(Page<InvitationEntity> page, InvitationEntity entity);

    @Override
    InvitationEntity selectById(Serializable id);

    void deleteByCommunity(Long communityId);
}
